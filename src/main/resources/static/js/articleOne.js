$().ready(function() {
    const articleId = $("#article").data("articleid")
    const currentUrl = window.location.pathname + window.location.search
    $(".close").click(function() {
        location.reload();
    })
	$("#article-modify").click(function() {
		$("#modal-wrapper").removeClass("hidden")
		$("#password-submit-btn").click(function() {
			articleHandler(articleId, "modify");
		})
	})
	$("#article-delete").click(function() {
		$("#modal-wrapper").removeClass("hidden")
		$("#password-submit-btn").click(function() {
			articleHandler(articleId, "delete");
		})
	})
    $(".reply-delete").click(function() {
		let replyId = $(this).closest(".reply-item").data("id");
        let action = 'delete'
		$("#modal-wrapper").removeClass("hidden")
		$("#password-submit-btn").click(function() {
            replyPasswordHandler(replyId, action, currentUrl);
		})
    })

    $(".reply-modify").click(function() {
        let replyId = $(this).closest(".reply-item").data("id");
        let action = 'modify'
		$("#modal-wrapper").removeClass("hidden")
		$("#password-submit-btn").click(function() {
            replyPasswordHandler(replyId, action, currentUrl);
		})
    })

    $("#reply-submit").click(function() {
        $("#input-current-url").val(currentUrl)
        let action = $("#reply-form-action").val();
        replySubmitHandler(action);
    })
    
})


        
function replySubmitHandler(action) {
    var isValid;
    if (action == 'write') {
        isValid = checkValidReply();
        if (isValid.result) {
            $("#reply-form").attr("action", "/reply/write")
            $("#reply-form").submit()
        } else {
            alert(isValid.msg)
        }
    } else if (action == 'modify') {
        isValid = checkValidReplyContent();
        if (isValid.result) {
            $("#reply-form").attr("action", '/reply/modify.do')
            $("#reply-form").submit()
        } else {
            alert(isValid.msg)
        }
    }
}

function replyPasswordHandler(replyId, action, currentUrl) {
    let replyForm = $("#modal-password-form")
    replyForm.attr("method", "post")

    let replyIdDom = $('<input type="hidden"/>')
    replyIdDom.attr("name", "replyId")
    replyIdDom.val(replyId)
    replyForm.append(replyIdDom)

    let replyPwDom = $("#password-confirm")
    replyPwDom.attr("name", "replyPassword")

    if (action == 'modify') {
        replyForm.attr("action", currentUrl)
    } else if (action == 'delete') {
        replyForm.attr("action", "/reply/delete")

        let urlDom = $('<input type="hidden"/>')
        urlDom.attr("name", "currentUrl")
        urlDom.val(currentUrl);
        replyForm.append(urlDom)
    }

    replyForm.submit()
}

function articleHandler(articleId, action) {
    let articleForm = $("#modal-password-form")
    articleForm.attr("action", action)
    articleForm.attr("method", "post")
    let articleIdDom = $('<input type="hidden"/>')
    articleIdDom.attr("name", "articleId")
    articleIdDom.val(articleId);
    articleForm.append(articleIdDom)
    let articlePwDom = $("#password-confirm")
    articlePwDom.attr("name", "articlePassword")
    articleForm.submit()
}

function checkValidReply() {
	var memName = $("#member-name").val();
	var repPassword = $("#reply-password").val();
	var repContent = $("#reply-write").val();
	if (memName && repPassword && repContent) {
		if (memName.length > 12) {
			return {result: false, msg:"작성자명은 12자 이내로 입력해주세요"}
		}
		if (repPassword.length < 4 || repPassword.length > 12) {
			return {result: false, msg:"비밀번호는 4자 이상 12자 이하로 입력해주세요"}
		}
		if (repContent.length > 200) {
			return {result: false, msg:"댓글 내용은 최대 200자까지 입력 가능합니다"}
		}
		return {result: true, msg:"성공"};
	}
	else {
		if (!memName) {
			return {result: false, msg:"작성자명을 입력해주세요"}
		}
		if (!repPassword) {
			return {result: false, msg:"댓글 비밀번호를 입력해주세요"}
		}
		if (!repContent) {
			return {result: false, msg:"댓글 내용을 입력해주세요"}
		}
	}
}

function checkValidReplyContent() {
    var repContent = $("#reply-write").val();
    if(repContent) {
        if (repContent.length > 200) {
            return {result: false, msg:"댓글 내용은 최대 200자까지 입력 가능합니다"}
        } else {
            return {result: true, msg:"성공"};
        }
    } else {
        return {result: false, msg:"댓글 내용을 입력해주세요"}
    }

}