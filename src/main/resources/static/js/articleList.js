$().ready(function() {
    $("#search-btn").click(function() {
        movePage()
    })
})
function movePage(pageNo = 0) {
    $("#pageNo").val(pageNo)
    $("#search-form").attr({
        "method": "get",
        "action": window.location.pathname
    }).submit()
}