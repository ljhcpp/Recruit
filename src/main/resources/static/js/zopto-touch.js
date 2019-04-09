(function($){
          var touch = {},
            touchTimeout, tapTimeout, swipeTimeout, longTapTimeout,
            longTapDelay = 750,
            gesture,
            down, up, move,
            eventMap,
            initialized = false
          function swipeDirection(x1, x2, y1, y2) {
            return Math.abs(x1 - x2) >=
              Math.abs(y1 - y2) ? (x1 - x2 > 0 ? 'Left' : 'Right') : (y1 - y2 > 0 ? 'Up' : 'Down')
          }
          function longTap() {
            longTapTimeout = null
            if (touch.last) {
              touch.el.trigger('longTap')
              touch = {}
            }
          }
          function cancelLongTap() {
            if (longTapTimeout) clearTimeout(longTapTimeout)
            longTapTimeout = null
          }
          function cancelAll() {
            if (touchTimeout) clearTimeout(touchTimeout)
            if (tapTimeout) clearTimeout(tapTimeout)
            if (swipeTimeout) clearTimeout(swipeTimeout)
            if (longTapTimeout) clearTimeout(longTapTimeout)
            touchTimeout = tapTimeout = swipeTimeout = longTapTimeout = null
            touch = {}
          }
          function isPrimaryTouch(event){
            return (event.pointerType == 'touch' ||
              event.pointerType == event.MSPOINTER_TYPE_TOUCH)
              && event.isPrimary
          }
          function isPointerEventType(e, type){
            return (e.type == 'pointer'+type ||
              e.type.toLowerCase() == 'mspointer'+type)
          }
          function unregisterTouchEvents(){
            if (!initialized) return
            $(document).off(eventMap.down, down)
              .off(eventMap.up, up)
              .off(eventMap.move, move)
              .off(eventMap.cancel, cancelAll)
            $(window).off('scroll', cancelAll)
            cancelAll()
            initialized = false
          }
          function setup(__eventMap){
            var now, delta, deltaX = 0, deltaY = 0, firstTouch, _isPointerType
            unregisterTouchEvents()
            eventMap = (__eventMap && ('down' in __eventMap)) ? __eventMap :
              ('ontouchstart' in document ?
              { 'down': 'touchstart', 'up': 'touchend',
                'move': 'touchmove', 'cancel': 'touchcancel' } :
              'onpointerdown' in document ?
              { 'down': 'pointerdown', 'up': 'pointerup',
                'move': 'pointermove', 'cancel': 'pointercancel' } :
               'onmspointerdown' in document ?
              { 'down': 'MSPointerDown', 'up': 'MSPointerUp',
                'move': 'MSPointerMove', 'cancel': 'MSPointerCancel' } : false)
        
            // No API availables for touch events
            if (!eventMap) return
            if ('MSGesture' in window) {
              gesture = new MSGesture()
              gesture.target = document.body
              $(document)
                .bind('MSGestureEnd', function(e){
                  var swipeDirectionFromVelocity =
                    e.velocityX > 1 ? 'Right' : e.velocityX < -1 ? 'Left' : e.velocityY > 1 ? 'Down' : e.velocityY < -1 ? 'Up' : null
                  if (swipeDirectionFromVelocity) {
                    touch.el.trigger('swipe')
                    touch.el.trigger('swipe'+ swipeDirectionFromVelocity)
                  }
                })
            }
            down = function(e){
              if((_isPointerType = isPointerEventType(e, 'down')) &&
                !isPrimaryTouch(e)) return
              firstTouch = _isPointerType ? e : e.touches[0]
              if (e.touches && e.touches.length === 1 && touch.x2) {
                touch.x2 = undefined
                touch.y2 = undefined
              }
              now = Date.now()
              delta = now - (touch.last || now)
              touch.el = $('tagName' in firstTouch.target ?
                firstTouch.target : firstTouch.target.parentNode)
              touchTimeout && clearTimeout(touchTimeout)
              touch.x1 = firstTouch.pageX
              touch.y1 = firstTouch.pageY
              if (delta > 0 && delta <= 250) touch.isDoubleTap = true
              touch.last = now
              longTapTimeout = setTimeout(longTap, longTapDelay)
              if (gesture && _isPointerType) gesture.addPointer(e.pointerId)
            }
            move = function(e){
              if((_isPointerType = isPointerEventType(e, 'move')) &&
                !isPrimaryTouch(e)) return
              firstTouch = _isPointerType ? e : e.touches[0]
              cancelLongTap()
              touch.x2 = firstTouch.pageX
              touch.y2 = firstTouch.pageY
              deltaX += Math.abs(touch.x1 - touch.x2)
              deltaY += Math.abs(touch.y1 - touch.y2)
            }
            up = function(e){
              if((_isPointerType = isPointerEventType(e, 'up')) &&
                !isPrimaryTouch(e)) return
              cancelLongTap()
              if ((touch.x2 && Math.abs(touch.x1 - touch.x2) > 30) ||
                  (touch.y2 && Math.abs(touch.y1 - touch.y2) > 30))
                swipeTimeout = setTimeout(function() {
                  if (touch.el){
                    touch.el.trigger('swipe')
                    touch.el.trigger('swipe' + (swipeDirection(touch.x1, touch.x2, touch.y1, touch.y2)))
                  }
                  touch = {}
                }, 0)
              else if ('last' in touch)
                if (deltaX < 30 && deltaY < 30) {
                  tapTimeout = setTimeout(function() {
                    var event = $.Event('tap')
                    event.cancelTouch = cancelAll
                    if (touch.el) touch.el.trigger(event)
                    if (touch.isDoubleTap) {
                      if (touch.el) touch.el.trigger('doubleTap')
                      touch = {}
                    }
                    else {
                      touchTimeout = setTimeout(function(){
                        touchTimeout = null
                        if (touch.el) touch.el.trigger('singleTap')
                        touch = {}
                      }, 250)
                    }
                  }, 0)
                } else {
                  touch = {}
                }
                deltaX = deltaY = 0
            }
            $(document).on(eventMap.up, up)
              .on(eventMap.down, down)
              .on(eventMap.move, move)
            $(document).on(eventMap.cancel, cancelAll)
            $(window).on('scroll', cancelAll)
            initialized = true
          }
          ;['swipe', 'swipeLeft', 'swipeRight', 'swipeUp', 'swipeDown',
            'doubleTap', 'tap', 'singleTap', 'longTap'].forEach(function(eventName){
            $.fn[eventName] = function(callback){ return this.on(eventName, callback) }
          })
          $.touch = { setup: setup }
          $(document).ready(setup)
        })(Zepto)