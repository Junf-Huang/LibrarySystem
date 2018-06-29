$(document).ready(function () {

    //load img
    var src = $('#photoName').text();
    if(src!='') {
        src = "/resource/books/" + src;
        $('#cover').attr("src", src);
    }

    //hide nav
    var page = $('#page').text();
    if(page == 0){
        console.log("page=",page)
        $('#page').next().hide();
    }

    //show modal
    $('.evaluator').on('click',function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $('.evaluatorForm #exampleModal').modal();
    });


});
