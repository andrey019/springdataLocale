<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link rel="icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/modal.css" rel="stylesheet">
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

<nav class="navbar navbar-inverse navbar-fixed-top" style="color: #3e8f3e">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">

            </button>
            <a class="navbar-brand" style="font-size: 160%; padding-left: 30px">WunderWaffel</a>
        </div>

        <p id="navbarText" class="navbar-text" style="font-size: 115%; padding-left: 145px; width: 50%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis"></p>
        <form class="navbar-form navbar-right" style="padding-right: 30px" action="/auth/logout">
            <button onclick="refresh(event)" type="submit" class="btn btn-info">Refresh</button>
            <button id="profileButton" type="submit" class="btn btn-info">Profile</button>
            <button id="donateModalButton" type="submit" class="btn btn-info">Donate</button>
            <button type="submit" class="btn btn-info">Sign Out</button>
        </form>
    </div>
    </div>
</nav>

<div style="width: 100%; overflow: hidden; padding-top: 70px">
    <div style="width: 337px; float: left; position: fixed; bottom: 150px; height: auto; top: 70px; overflow-y: auto;">
        <div style="width: 290px; float: left; margin-left: 15px">
            <div class="row" style="width: 320px">
                <div class="col-lg-6" style="width: 100%">
                    <div class="input-group" style="width: 100%">
                        <input id="findTodoInput" type="text" class="form-control" onkeyup="findTodoEnter(event)" placeholder="Search TODO...">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" onclick="findTodo()" style="font-size: 20px"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                    </span>
                    </div>
                </div>
            </div>
            <br>
            <div id="listResult" class="list-group">

            </div>
        </div>
    </div>

    <div style="margin-left: 340px; width: auto">
        <div id="addTodoDiv" class="row" style="width: 100%">
            <div class="col-lg-6" style="width: 100%">
                <div class="input-group" style="width: 100%">
                    <input id="addTodoInput" type="text" onkeyup="addTodoInputEnter(event)" class="form-control" placeholder="Add new TODO...">
                    <span class="input-group-btn">
                        <button onclick="addTodo()" class="btn btn-default" type="button" style="font-size: 20px"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>
                    </span>
                </div>
            </div>
        </div>
        <br><br>
        <div id="searchResult" style="width: 100%; padding-right: 30px">

        </div>
        <div style="width: 100%">
            <div id="todoResult" class="list-group" style="padding-right: 30px">

            </div>
        </div>
        <button id="showDoneTodosButton" onclick="showDoneTodosClick()" class="btn btn-default" type="button">Show done todos...</button>
        <br><br>
        <div style="width: 100%">
            <div id="doneTodoResult" class="list-group" style="padding-right: 30px">

            </div>
        </div>
        <br><br>
    </div>


    <div style="position: fixed; bottom: 20px; width: 320px; margin-left: 15px">
        <div class="row" style="width: 100%">
            <div class="col-lg-6" style="width: 100%">
                <div class="input-group" style="width: 100%">
                    <input id="addTodoListInput" type="text" onkeyup="addTodoListInputEnter(event)" class="form-control" placeholder="Add new LIST...">
                    <span class="input-group-btn">
                        <button onclick="addTodoList()" class="btn btn-default" type="button" style="font-size: 20px"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button>
                    </span>
                </div>
            </div>
        </div>
        <br>
        <div class="btn-group btn-group-justified" role="group" aria-label="..." style="padding-right: 30px">
            <a id="deleteModalButton" type="button" class="btn btn-danger">Delete</a>
            <a id="shareModalButton" type="button" class="btn btn-success">Share</a>
        </div>
    </div>

</div>


<div id="profileModal" class="modal">
    <div class="modal-content">
        <span id="closeSpan" class="close">×</span><br>
        <p style="font-size: 130%; text-align: center">User info</p>
        <p>Leave fields that you don't want to change empty</p>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span>
            <input id="proEmailInput" type="email" class="form-control" aria-describedby="basic-addon1" disabled>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></span>
            <input id="proFNameInput" type="text" class="form-control" aria-describedby="basic-addon1 required">
        </div>
        <br>
        <div class="input-group" style="border-color: red">
            <span class="input-group-addon"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></span>
            <input id="proLNameInput" type="text" name="lName" class="form-control" aria-describedby="basic-addon1 required">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
            <input id="proPassInput" onblur="passCheck()" type="password" name="pass" class="form-control" placeholder="New password" aria-describedby="basic-addon1 required">
        </div>
        <div hidden id="proPassError" class="alert alert-danger" role="alert">
            <p>Your password must be 6-20 characters!</p>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
            <input id="proRepeatPassInput" onblur="repeatPassCheck()" type="password" class="form-control" placeholder="Repeat new password" aria-describedby="basic-addon1" required>
        </div>
        <div hidden id="proRepeatPassError" class="alert alert-danger" role="alert">
            <p>Your passwords doesn't match!</p>
        </div>
        <br>
        <br><br>
        <button type="button" onclick="updateProfile()" class="btn btn-primary" style="width: 100%">Update</button>
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
        <p id="delTodoListHeader" style="font-size: 130%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis"></p>
        <p>Users with which you share this list:</p>
        <div style="width: 100%">
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
        <p id="shareTodoListHeader" style="font-size: 130%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis"></p>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span>
            <input id="shareEmailInput" onkeyup="shareUserEnter(event)" placeholder="User email" type="email" class="form-control" aria-describedby="basic-addon1">
        </div>
        <br>
        <button id="shareButton" type="button" onclick="shareUser()" class="btn btn-success" style="width: 100%">Share</button>
        <br>
        <div hidden id="shareSuccess" class="alert alert-success" role="alert">
            <p>Your list is shared!</p>
        </div>
        <div hidden id="shareError" class="alert alert-danger" role="alert">
            <p id="shareErrorText"></p>
        </div>
        <br>

        <p style="word-wrap: normal"><b>Users with which you share this list (click to delete):</b></p>
        <div style="width: 100%">
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
        <p id="donateHeader" style="font-size: 130%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; text-align: center">Already donated 999 UAH</p>
        <p>You can donate money with your credit card</p>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span>
            <input id="donateInput" type="number" class="form-control" placeholder="5 UAH minimum" aria-describedby="basic-addon1" required>
        </div>
        <div hidden id="donateInputError" class="alert alert-danger" role="alert">
            <p id="donateInputErrorText">Wrong value!</p>
        </div>
        <br>
        <button id="donateButton" type="button" onclick="donate()" class="btn btn-success" style="width: 100%">Donate</button>
        <br>
        <div hidden id="donateError" class="alert alert-danger" role="alert">
            <p id="donateErrorText">Error occurred!</p>
        </div>
        <div hidden id="donateFormContainer"></div>
    </div>
</div>

</body>
</html>
