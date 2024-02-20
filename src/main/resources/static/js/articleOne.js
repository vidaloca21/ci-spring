const pageUtil = {
    articleId: '',
	boardurl: '',
	
    replySubmitHandler: function(action) {
        var isValid;
        if (action == 'write') {
            isValid = this.checkValidReply();
            if (isValid.result) {
                $("#reply-form").attr("action", "/reply/write")

                $("#reply-form").submit()
            } else {
                alert(isValid.msg)
            }
        } else if (action == 'modify') {
            isValid = this.checkValidReplyContent();
            if (isValid.result) {
                $("#reply-form").attr("action", '/reply/modify.do')

                $("#reply-form").submit()
            } else {
                alert(isValid.msg)
            }
        }
    },

    replyPasswordHandler: function(replyId, action) {
        let replyForm = $("#modal-password-form")
        replyForm.attr("method", "post")
    
        let replyIdDom = $('<input type="hidden"/>')
        replyIdDom.attr("name", "replyId")
        replyIdDom.val(replyId)
        replyForm.append(replyIdDom)
    
        let replyPwDom = $("#password-confirm")
        replyPwDom.attr("name", "replyPassword")
    
        if (action == 'modify') {
            replyForm.attr("action", "")
        } else if (action == 'delete') {
            replyForm.attr("action", "/reply/delete")
        }
    
        replyForm.submit()
    },
    
    articleHandler: function(action) {
        let articleForm = $("#modal-password-form")
        if (action == "modify") {
	        articleForm.attr("action", "/"+this.boardurl+"/modify")
		} else if (action == "delete") {
    	    articleForm.attr("action", "/"+this.boardurl+"/delete")
		}
        articleForm.attr("method", "post")
        let articleIdDom = $('<input type="hidden"/>')
        articleIdDom.attr("name", "articleId")
        articleIdDom.val(this.articleId);
        articleForm.append(articleIdDom)
        let articlePwDom = $("#password-confirm")
        articlePwDom.attr("name", "articlePassword")

        articleForm.submit()
    },
    
    checkValidReply: function() {
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
    },
    
    checkValidReplyContent: function() {
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
    },
}

$().ready(function() {
    pageUtil.articleId = $("#article").data("articleid")
    pageUtil.boardurl = $("#article").data("boardurl")

    $(".close").click(function() {
        location.reload();
    })
	$("#article-modify").click(function() {
		$("#modal-wrapper").removeClass("hidden")
		$("#password-submit-btn").click(function() {
			pageUtil.articleHandler("modify");
		})
	})
	$("#article-delete").click(function() {
		$("#modal-wrapper").removeClass("hidden")
		$("#password-submit-btn").click(function() {
			pageUtil.articleHandler("delete");
		})
	})
    $(".reply-delete").click(function() {
		let replyId = $(this).closest(".reply-item").data("id");
		$("#modal-wrapper").removeClass("hidden")
		$("#password-submit-btn").click(function() {
            pageUtil.replyPasswordHandler(replyId, 'delete');
		})
    })

    $(".reply-modify").click(function() {
        let replyId = $(this).closest(".reply-item").data("id");
		$("#modal-wrapper").removeClass("hidden")
		$("#password-submit-btn").click(function() {
            pageUtil.replyPasswordHandler(replyId, 'modify');
		})
    })

    $("#reply-submit").click(function() {
        let action = $("#reply-form-action").val();
        pageUtil.replySubmitHandler(action);
    })
    
})
        