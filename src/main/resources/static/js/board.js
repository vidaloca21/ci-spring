$().ready(function() {
    $("#search-btn").click(function() {
        movePage()
    })
})
function movePage(pageNo = 0) {
    $("#pageNo").val(pageNo)
    console.log($("#pageNo").val(pageNo))
    $("#search-form").attr({
        "method": "get",
        "action": "/board"
    }).submit()
}