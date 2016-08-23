<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <link rel="shortcut icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link rel="icon" href="/resources/images/favicon.ico" type="image\x-icon" />
    <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/modal.css" rel="stylesheet">
    <script type="text/javascript" src="/resources/js/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/mainPageScripts.js"></script>
    <sec:csrfMetaTags />
    <meta http-equiv="refresh" content="${pageContext.session.maxInactiveInterval}">
    <title>WunderWaffel</title>
</head>
<body style="background-image: url('/resources/images/background2.jpg'); background-attachment: fixed">

<nav class="navbar navbar-inverse navbar-fixed-top" style="color: #3e8f3e">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">

            </button>
            <a class="navbar-brand" href="#" style="font-size: 160%; padding-left: 30px">WunderWaffel</a>
        </div>

        <p id="navbarText" class="navbar-text" style="font-size: 115%; padding-left: 145px; width: 60%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis"></p>
        <form class="navbar-form navbar-right" style="padding-right: 30px">
            <button id="registrationButton" type="submit" class="btn btn-success">Create free account</button>
        </form>
    </div>
    </div>
</nav>


<div id="signInDiv" style="width: 400px; height: 300px; position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto;">
    <c:if test="${param.error != null}">
        <div class="alert alert-danger" role="alert">
            Email or password is incorrect!
        </div>
    </c:if>
    <c:if test="${requestScope['confirm'] == 'ok'}">
        <div class="alert alert-success" role="alert">
            <p>Registration is complete, you can sign in now!</p>
        </div>
    </c:if>
    <c:if test="${requestScope['confirm'] == 'error'}">
        <div class="alert alert-danger" role="alert">
            <p>Registration error!</p>
        </div>
    </c:if>
    <form method="post" action="/">
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span>
            <input id="emailInput" name="email" type="email" class="form-control" placeholder="Email" aria-describedby="basic-addon1" required>
        </div>
        <p></p>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
            <input id="passwordInput" name="password" type="password" class="form-control" placeholder="Password" aria-describedby="basic-addon1" required>
        </div>
        <p></p>
        <div hidden class="input-group input-sm">
            <div hidden class="checkbox">
                <input hidden checked type="checkbox" id="rememberme" name="remember-me">
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />
        <div class="form-actions">
            <input type="submit" class="btn btn-block btn-primary" value="Sign in">
        </div><br>
        <button id="recoveryButton" type="button" class="btn btn-danger" style="width: 100%">Can't remember my password...</button>
    </form>

</div>



<div id="registrationModal" class="modal">
    <div class="modal-content">
        <span id="closeSpan" class="close">×</span><br>
        <p style="font-size: 130%; text-align: center">Registration form</p>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span>
            <input id="regEmailInput" onblur="emailCheck()" type="email" class="form-control" placeholder="Email" aria-describedby="basic-addon1" required>
        </div>
        <div hidden id="regEmailError" class="alert alert-danger" role="alert">
            <p id="regEmailErrorText"></p>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></span>
            <input id="regFNameInput" onblur="fNameCheck()" type="text" class="form-control" placeholder="First name" aria-describedby="basic-addon1 required">
        </div>
        <div hidden id="regFNameError" class="alert alert-danger" role="alert">
            <p>You must enter your first name!</p>
        </div>
        <br>
        <div class="input-group" style="border-color: red">
            <span class="input-group-addon"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></span>
            <input id="regLNameInput" onblur="lNameCheck()" type="text" class="form-control" placeholder="Last name" aria-describedby="basic-addon1 required">
        </div>
        <div hidden id="regLNameError" class="alert alert-danger" role="alert">
            <p>You must enter your last name!</p>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
            <input id="regPassInput" onblur="passCheck()" type="password" class="form-control" placeholder="Password" aria-describedby="basic-addon1 required">
        </div>
        <div hidden id="regPassError" class="alert alert-danger" role="alert">
            <p>Your password must be 6-20 characters!</p>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
            <input id="regRepeatPassInput" onblur="repeatPassCheck()" type="password" class="form-control" placeholder="Repeat password" aria-describedby="basic-addon1" required>
        </div>
        <div hidden id="regRepeatPassError" class="alert alert-danger" role="alert">
            <p>Your passwords doesn't match!</p>
        </div>
        <br>
        <br><br>
        <button id="regButton" type="button" onclick="registration()" class="btn btn-primary" style="width: 100%">Sign up!</button>
        <br><br>
        <div hidden id="regSuccess" class="alert alert-success" role="alert">
            <p>Your form is accepted!<br>
                Please, check your email to complete registration.<br>
                Note that the letter may be sent in a couple of minutes</p>
        </div>
        <div hidden id="regError" class="alert alert-danger" role="alert">
            <p id="regErrorText"></p>
        </div>
    </div>
</div>



<div id="recoveryModal" class="modal">
    <div class="modal-content">
        <span id="recCloseSpan" class="close">×</span><br>
        <p style="font-size: 130%; text-align: center">Password recovery</p>
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span>
            <input id="recEmailInput" type="email" class="form-control" placeholder="Email" aria-describedby="basic-addon1" required>
        </div>
        <br><br>
        <button id="recButton" type="button" onclick="passwordRecovery()" class="btn btn-primary" style="width: 100%">Recover</button>
        <br><br>
        <div hidden id="recSuccess" class="alert alert-success" role="alert">
            <p>Recovery successful!<br>
                Please, check your email to get new password.<br>
                Note that the letter may be sent in a couple of minutes</p>
        </div>
        <div hidden id="recError" class="alert alert-danger" role="alert">
            <p id="recErrorText"></p>
        </div>
    </div>
</div>



</body>
</html>
