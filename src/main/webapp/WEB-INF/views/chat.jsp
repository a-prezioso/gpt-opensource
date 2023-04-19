<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="dev.alexprezioso.gpt.util.Utils" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat with GPT</title>
    <!-- Add Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Add jQuery and Bootstrap JS -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .list-group-item pre {
            white-space: pre-wrap;
            word-wrap: break-word;
        }
    </style>
</head>
<body>
<div class="container">

    <div class="text-right mt-2">
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <button type="submit" class="btn btn-secondary">Logout</button>
        </form>
    </div>
    <br>
    <h1 class="text-center mt-4">Chat with GPT</h1>
    <form action="/chat" method="post" class="mt-4">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <div class="form-group">
            <label for="context">Context:</label>
            <input type="text" id="context" name="context" class="form-control" required />
        </div>

        <div class="form-group">
            <label for="message">Your message:</label>
            <textarea id="message" name="message" class="form-control" required></textarea>
        </div>

        <button type="submit" class="btn btn-primary">Send</button>
    </form>
    <h2 class="mt-4">Conversation history:</h2>
    <ul class="list-group">
        <c:forEach items="${conversation}" var="message" varStatus="status">
            <li class="list-group-item">
                <strong>${conversation[conversation.size() - 1 - status.index].role}:</strong>
                <pre>${fn:escapeXml(Utils.formatCodeInText(conversation[conversation.size() - 1 - status.index].content))}</pre>
            </li>
        </c:forEach>

    </ul>
</div>
<script>
    $(document).ready(function () {
        $('#message').on('input', function () {
            this.style.height = 'auto';
            this.style.height = (this.scrollHeight) + 'px';
        });
    });
</script>

<script>
    $(document).ready(function() {
        $('#message').keydown(function(event) {
            if (event.keyCode == 13 && event.shiftKey) {
                var content = this.value;
                var caret = getCaret(this);
                this.value = content.substring(0, caret) + "\n" + content.substring(caret);
                this.setSelectionRange(caret + 1, caret + 1);
                event.preventDefault();
            } else if (event.keyCode == 13) {
                event.preventDefault();
                $('form').submit();
            }
        });
    });

    // Helper function to get the caret position in a textarea
    function getCaret(el) {
        if (el.selectionStart) {
            return el.selectionStart;
        } else if (document.selection) {
            el.focus();
            var r = document.selection.createRange();
            if (r == null) {
                return 0;
            }
            var re = el.createTextRange(),
                rc = re.duplicate();
            re.moveToBookmark(r.getBookmark());
            rc.setEndPoint('EndToStart', re);
            return rc.text.length;
        }
        return 0;
    }
</script>

</body>
</html>
