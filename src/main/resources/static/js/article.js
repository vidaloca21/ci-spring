$().ready(function() {
    $(".reply-delete").click(function() {
        console.log($(this).closest(".reply-item").attr("id"))
    })  
})