// 获取弹窗
var modal = document.getElementById('myModal');
var modal2 = document.getElementById('myModal2');

// 打开弹窗的按钮对象
var btn2 = document.getElementsByClassName("details")[0];
var btn = document.getElementsByClassName("ti")[0];

// 获取 <span> 元素，用于关闭弹窗 that closes the modal
var span = document.getElementsByClassName("close")[0];
var span1 = document.getElementsByClassName("close")[1];

// 点击按钮打开弹窗
// btn.onclick = function() {
//     modal.style.display = "block";
// }
btn.onclick = function() {
    modal.style.display = "block";
}
btn2.onclick = function() {
    modal2.style.display = "block";
}
// 点击 <span> (x), 关闭弹窗
span.onclick = function() {
    modal.style.display = "none";
}
span1.onclick = function() {
    modal2.style.display = "none";
}
// 在用户点击其他地方时，关闭弹窗
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
};
// $("#btn").click(function () {
//     var txt = $(".txt");
//     var bb="";
//     for(let i=0;i<txt.length;i++){
//         bb = bb + $(txt[i]).text() + ",";
//     }
//     bb=bb.substring(0,bb.length-1);
//     //console.log(bb);
//     var score = $('.score');
//     var aa="";
//     for(let i=0;i<score.length;i++){
//         aa=aa+$(score[i]).val()+",";
//     }
//     console.log(aa);
//     aa=aa.substring(0,aa.length-1);
//     var cc =bb+"-"+aa;
//     var postData = {
//         "detail": cc
//     }
//     $.ajax({
//         async: false,
//         cache: false,
//         type: 'POST',
//         url: '/interview/submitInterview',
//         data: postData,
//         error: function () {
//             alert("123")
//         },
//         success: function (data) {
//              alert("成功")
//         }
//     });
// });


$(".btn").on('click',function () {
    var txt = $(".txt");
    var bb="";
    for(let i=0;i<txt.length;i++){
        bb = bb + $(txt[i]).text() + ",";
    }
    bb=bb.substring(0,bb.length-1);
    //console.log(bb);
    var score = $('.score');
    var aa="";
    for(let i=0;i<score.length;i++){
        aa=aa+$(score[i]).val()+",";
    }
    //console.log(aa);
    aa=aa.substring(0,aa.length-1);
    var cc =bb+"-"+aa;
    console.log('cc='+cc);
    console.log($('.postData').val());
    $('.postData').val(cc);
    console.log($('.postData').val());
    $(".btn").attr('type','submit');
});

        // var postData = {
        //     "detail": cc
        // };

        // $.ajax({
        //     async: false,
        //     cache: false,
        //     type: 'POST',
        //     url: '/interview/submitInterview',
        //     data: postData,
        //     error: function () {
        //         alert("123")
        //     },
        //     success: function (data) {
        //         // alert("成功")
        //         $('.cure').addClass('uu');
        //         $('.cure').html('打分成功');
        //         setInterval(function () {
        //             $('.cure').css('display','none');
        //         },2000);
        //         setTimeout(function () {
        //             location.reload(true);
        //         },1000);
        //
        //     }
        // });




