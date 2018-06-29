$(document).ready(function () {

    var page = $('#page').text();
    if(page == 0){
        console.log("page=",page)
        $('#page').next().hide();
    }

    $('#bookPhoto').hide(); //隐藏input

    $('#imgInp').on('change',function(){

        readURL(this);

        //get the file name
        var fileName = $(this).val().replace(/.*(\/|\\)/, "");
        //replace the "Choose a file" label
        $(this).next('.custom-file-label').html(fileName);
        $('#bookPhoto').val(fileName);
    });

    function readURL(input) {

        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function(e) {
                $('#blah').attr('src', e.target.result)
                    .width(250).height(313);
            }

            reader.readAsDataURL(input.files[0]);
        }
    }

    $('.nBtn, .table .eBtn').on('click',function (event) {
        event.preventDefault();
        $('.myForm #bookISBN').attr("readonly", false);
        var href = $(this).attr('href');
        var test = $(this).text();
        if(test=='Edit') {
            $.get(href, function (book, status) {
                $('.myForm #bookISBN').val(book.bookISBN);
                $('.myForm #bookISBN').attr("readonly", true);  //添加readonly属性
                $('.myForm #bookName').val(book.bookName);
                $('.myForm #publisher').val(book.publisher);
                $('.myForm #translator').val(book.translator);

                console.log("photoName: "+book.bookPhoto);
                if(book.bookPhoto == null || book.bookPhoto == "")
                    $('.myForm #blah').attr("src","/cover/cover.svg");
                else
                    $('.myForm #blah').attr("src","/resource/books/" + book.bookPhoto);

                $('.myForm #bookPhoto').val(book.bookPhoto);
                $('.myForm #bookPrice').val(book.bookPrice);
                $('.myForm #replicateQuantity').val(book.replicateQuantity);
                $('.myForm #stockQuantity').val(book.stockQuantity);
                $('.myForm #bookSummary').val(book.bookSummary);
            });
        }else {
            $('.myForm #bookISBN').val('');
            $('.myForm #bookName').val('');
            $('.myForm #publisher').val('');
            $('.myForm #translator').val('');
            $('.myForm #blah').attr("src","/cover/cover.svg");
            $('.myForm #bookPhoto').val('');
            $('.myForm #bookPrice').val('');
            $('.myForm #replicateQuantity').val('');
            $('.myForm #stockQuantity').val('');
            $('.myForm #bookSummary').val('');
        }

        $('.myForm #exampleModal').modal();
    });

    $('.table .delBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #delRef').attr('href',href);
        href = $('#myModal #delRef').attr('href');
        $('#myModal').modal();
    });

});