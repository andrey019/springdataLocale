<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link rel="icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/styles.css" rel="stylesheet">
    <script type="text/javascript" src="/resources/js/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/userPageScripts.js"></script>
    <meta http-equiv="refresh" content="${pageContext.session.maxInactiveInterval}">
    <sec:csrfMetaTags />
    <sec:authorize access="isAuthenticated()">
        <sec:authentication var="username" property="principal.username" />
        <title>WunderWaffel ${username}</title>
    </sec:authorize>
</head>
<body style="background: url('/resources/images/background2.jpg'); background-attachment: fixed; background-size: cover">

<nav id="navbarColor" class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">

            </button>
            <a id="navbarBrand" class="navbar-brand" href="#">WunderWaffel</a>
        </div>

        <p id="navbarText" class="navbar-text"></p>
        <form id="navbarRight" class="navbar-form navbar-right" action="/auth/logout">
            <button onclick="refresh()" type="button" class="btn btn-info">Refresh</button>
            <button id="profileButton" type="button" class="btn btn-info">Profile</button>
            <button id="donateModalButton" type="button" class="btn btn-info">Donate</button>
            <button type="submit" class="btn btn-info">Sign Out</button>
        </form>
    </div>
    </div>
</nav>

<div id="mainDiv">
    <div id="leftMenuTop">
        <div id="leftTopChild">
            <div id="leftTopRow" class="row">
                <div class="col-lg-6 width100">
                    <div class="input-group width100">
                        <input id="findTodoInput" type="text" class="form-control" onkeyup="findTodoEnter(event)" placeholder="Search TODO...">
                    <span class="input-group-btn">
                        <button class="btn btn-default btnGlyph" type="button" onclick="findTodo()"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                    </span>
                    </div>
                </div>
            </div>
            <br>
            <div id="listResult" class="list-group">

            </div>
        </div>
    </div>

    <div id="rightMenuBottom">
        <div id="addTodoDiv" class="row width100">
            <div class="col-lg-6 width100">
                <div class="input-group width100">
                    <input id="addTodoInput" type="text" onkeyup="addTodoInputEnter(event)" class="form-control" placeholder="Add new TODO...">
                    <span class="input-group-btn">
                        <button onclick="addTodo()" class="btn btn-default btnGlyph" type="button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>
                    </span>
                </div>
            </div>
        </div>
        <br><br>
        <div id="searchResult">

        </div>
        <div class="width100">
            <div id="todoResult" class="list-group">

            </div>
        </div>
        <button id="showDoneTodosButton" onclick="showDoneTodosClick()" class="btn btn-default" type="button">Show done todos...</button>
        <br><br>
        <div class="width100">
            <div id="doneTodoResult" class="list-group">

            </div>
        </div>
        <br><br>
    </div>


    <div id="bottomMenu">
        <div class="row width100">
            <div class="col-lg-6 width100">
                <div class="input-group width100">
                    <input id="addTodoListInput" type="text" onkeyup="addTodoListInputEnter(event)" class="form-control" placeholder="Add new LIST...">
                    <span class="input-group-btn">
                        <button onclick="addTodoList()" class="btn btn-default btnGlyph" type="button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>
                    </span>
                </div>
            </div>
        </div>
        <br>
        <div id="bottomButtons" class="btn-group btn-group-justified" role="group" aria-label="...">
            <a id="deleteModalButton" type="button" class="btn btn-danger">Delete</a>
            <a id="shareModalButton" type="button" class="btn btn-success">Share</a>
        </div>
    </div>

</div>


<div id="profileModal" class="modal">
    <div class="modal-content">
        <span id="closeSpan" class="close">×</span><br>
        <p class="modal-header modalHeader">User info</p>
        <p>Leave fields that you don't want to change empty</p>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span>
            <input id="proEmailInput" type="email" class="form-control" aria-describedby="basic-addon1" disabled>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></span>
            <input id="proFNameInput" type="text" onkeyup="onProfileEnter(event)" class="form-control" aria-describedby="basic-addon1 required">
        </div>
        <br>
        <div class="input-group" style="border-color: red">
            <span class="input-group-addon"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></span>
            <input id="proLNameInput" type="text" onkeyup="onProfileEnter(event)" name="lName" class="form-control" aria-describedby="basic-addon1 required">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
            <input id="proPassInput" onblur="passCheck()" onkeyup="onProfilePassEnter(event)" type="password" name="pass" class="form-control" placeholder="New password" aria-describedby="basic-addon1 required">
        </div>
        <div hidden id="proPassError" class="alert alert-danger" role="alert">
            <p>Your password must be 6-20 characters!</p>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
            <input id="proRepeatPassInput" onblur="repeatPassCheck()" onkeyup="onProfilePassEnter(event)" type="password" class="form-control" placeholder="Repeat new password" aria-describedby="basic-addon1" required>
        </div>
        <div hidden id="proRepeatPassError" class="alert alert-danger" role="alert">
            <p>Your passwords doesn't match!</p>
        </div>
        <br>
        <br><br>
        <button type="button" onclick="updateProfile()" class="btn btn-primary width100">Update</button>
        <br><br>
        <div hidden id="proSuccess" class="alert alert-success" role="alert">
            <p>Your info is updated!<br>
                New credentials would be sent to your email.<br>
                Note that the letter may be sent in a couple of minutes</p>
        </div>
        <div hidden id="proError" class="alert alert-danger" role="alert">
            <p id="proErrorText"></p>
        </div>
    </div>
</div>



<div id="deleteModal" class="modal">
    <div class="modal-content">
        <span id="delCloseSpan" class="close">×</span><br>
        <p id="delTodoListHeader" class="modal-header modalHeader"></p>
        <p>Users with which you share this list:</p>
        <div class="width100">
            <div id="delInfo" class="list-group">

            </div>
        </div>
        <br>
        <button id="deleteButton" type="button" onclick="deleteTodoList()" class="btn btn-danger" style="width: 100%">Delete</button>
        <br><br>
        <div hidden id="delSuccess" class="alert alert-success" role="alert">
            <p>Your list is deleted!</p>
        </div>
        <div hidden id="delError" class="alert alert-danger" role="alert">
            <p id="delErrorText"></p>
        </div>
    </div>
</div>



<div id="shareModal" class="modal">
    <div class="modal-content">
        <span id="shareCloseSpan" class="close">×</span><br>
        <p id="shareTodoListHeader" class="modal-header modalHeader"></p>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span>
            <input id="shareEmailInput" onkeyup="shareUserEnter(event)" placeholder="User email" type="email" class="form-control" aria-describedby="basic-addon1">
        </div>
        <br>
        <button id="shareButton" type="button" onclick="shareUser()" class="btn btn-success width100">Share</button>
        <br>
        <div hidden id="shareSuccess" class="alert alert-success" role="alert">
            <p>Your list is shared!</p>
        </div>
        <div hidden id="shareError" class="alert alert-danger" role="alert">
            <p id="shareErrorText"></p>
        </div>
        <br>

        <p style="word-wrap: normal"><b>Users with which you share this list (click to delete):</b></p>
        <div class="width100">
            <div id="sharedUsers" class="list-group">

            </div>
        </div>
        <br>
        <div hidden id="unShareSuccess" class="alert alert-success" role="alert">
            <p>User is deleted from your list!</p>
        </div>
        <div hidden id="unShareError" class="alert alert-danger" role="alert">
            <p id="unShareErrorText"></p>
        </div>
    </div>
</div>


<div id="donateModal" class="modal">
    <div class="modal-content">
        <span id="donateCloseSpan" class="close">×</span><br>
        <p id="donateHeader" class="modal-header modalHeader">Already donated   UAH</p>
        <p>You can donate some money if you'd like (via LiqPay)</p>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-piggy-bank" aria-hidden="true"></span></span>
            <input id="donateInput" type="number" onkeyup="donateInputEnter(event)" class="form-control" placeholder="1 UAH minimum" aria-describedby="basic-addon1" required>
        </div>
        <div hidden id="donateInputError" class="alert alert-danger" role="alert">
            <p id="donateInputErrorText">Wrong value!</p>
        </div>
        <br>
        <button id="donateButton" type="button" onclick="donate()" class="btn btn-success width100">Donate</button>
        <br>
        <div hidden id="donateError" class="alert alert-danger" role="alert">
            <p id="donateErrorText">Error occurred!</p>
        </div>
        <div hidden id="donateFormContainer"></div>
    </div>
</div>

</body>
</html>
