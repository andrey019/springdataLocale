
$(document).ready(function () {
    window.emailValidity = "no";

    document.getElementById("registrationButton").onclick = function() {
        onRecoveryClose();
        onContactsClose();
        document.getElementById("registrationModal").style.display = "block";
        $("#signInDiv").hide();
    };

    document.getElementById("closeSpan").onclick = function() {
        onRegFormClose();
    };

    document.getElementById("recoveryButton").onclick = function() {
        onRegFormClose();
        onContactsClose();
        document.getElementById("recoveryModal").style.display = "block";
        $("#signInDiv").hide();
    };

    document.getElementById("recCloseSpan").onclick = function() {
        onRecoveryClose();
    };

    document.getElementById("contactsButton").onclick = function() {
        onRecoveryClose();
        onRegFormClose();
        document.getElementById("contactsModal").style.display = "block";
        $("#signInDiv").hide();
    };

    document.getElementById("contactsCloseSpan").onclick = function() {
        onContactsClose();
    };

    window.onclick = function(event) {
        if (event.target == document.getElementById("recoveryModal")) {
            onRecoveryClose();
        }
        if (event.target == document.getElementById("registrationModal")) {
            onRegFormClose();
        }
        if (event.target == document.getElementById("contactsModal")) {
            onContactsClose();
        }
    };
});

function onRegFormClose() {
    document.getElementById("registrationModal").style.display = "none";
    $("#signInDiv").show();
    document.getElementById("regButton").disabled = false;
    document.getElementById("regEmailInput").value = "";
    document.getElementById("regFNameInput").value = "";
    document.getElementById("regLNameInput").value = "";
    document.getElementById("regPassInput").value = "";
    document.getElementById("regRepeatPassInput").value = "";
    $("#regEmailError").hide();
    $("#regFNameError").hide();
    $("#regLNameError").hide();
    $("#regPassError").hide();
    $("#regRepeatPassError").hide();
    $("#regSuccess").hide();
    $("#regError").hide();
}

function onRecoveryClose() {
    document.getElementById("recoveryModal").style.display = "none";
    $("#signInDiv").show();
    document.getElementById("recButton").disabled = false;
    document.getElementById("recEmailInput").value = "";
    $("#recSuccess").hide();
    $("#recError").hide();
}

function onContactsClose() {
    document.getElementById("contactsModal").style.display = "none";
    $("#signInDiv").show();
}

function emailCheck() {
    window.emailValidity = "no";
    var email = document.getElementById("regEmailInput").value;
    if (email == "") {
        document.getElementById("regEmailErrorText").innerHTML = "Enter your email!";
        $("#regEmailError").show();
        return;
    } else {
        $("#regEmailError").hide();
    }

    var jsonEmailCheck = {
        "email": email
    };

    $.ajax({
        type: "POST",
        url: "/auth/emailCheck",
        data: JSON.stringify(jsonEmailCheck),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data != "ok") {
                window.emailValidity = "no";
                document.getElementById("regEmailErrorText").innerHTML = data;
                $("#regEmailError").show();
            } else {
                window.emailValidity = "ok";
                $("#regEmailError").hide();
            }
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function fNameCheck() {
    if (document.getElementById("regFNameInput").value == "") {
        $("#regFNameError").show();
    } else {
        $("#regFNameError").hide();
    }
}

function lNameCheck() {
    if (document.getElementById("regLNameInput").value == "") {
        $("#regLNameError").show();
    } else {
        $("#regLNameError").hide();
    }
}

function passCheck() {
    var passLength = document.getElementById("regPassInput").value.length;
    if ( (passLength < 6) || (passLength > 20) ) {
        $("#regPassError").show();
    } else {
        $("#regPassError").hide();
    }
}

function repeatPassCheck() {
    if (document.getElementById("regPassInput").value != document.getElementById("regRepeatPassInput").value) {
        $("#regRepeatPassError").show();
    } else {
        $("#regRepeatPassError").hide();
    }
}

function registration() {
    if ($("#regEmailError").is(':visible') || $("#regFNameError").is(':visible') ||
        $("#regLNameError").is(':visible') || $("#regPassError").is(':visible') ||
        $("#regRepeatPassError").is(':visible') ||
        (document.getElementById("regEmailInput").value == "") ||
        (document.getElementById("regFNameInput").value == "") ||
        (document.getElementById("regLNameInput").value == "") ||
        (document.getElementById("regPassInput").value == "") ||
        (document.getElementById("regRepeatPassInput").value == "") || (window.emailValidity != "ok") ) {
        return;
    }

    var jsonRegistration = {
        "email": document.getElementById("regEmailInput").value,
        "password": document.getElementById("regPassInput").value,
        "fName": document.getElementById("regFNameInput").value,
        "lName": document.getElementById("regLNameInput").value
    };

    $.ajax({
        type: "POST",
        url: "/auth/registration",
        data: JSON.stringify(jsonRegistration),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data != "ok") {
                $("#regSuccess").hide();
                document.getElementById("regErrorText").innerHTML = data;
                $("#regError").show();
            } else {
                $("#regError").hide();
                $("#regSuccess").show();
                document.getElementById("regButton").disabled = true;
            }
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function passwordRecovery() {
    if (document.getElementById("recEmailInput").value == "") {
        return;
    }

    var email = {
        "email": document.getElementById("recEmailInput").value
    };

    $.ajax({
        type: "POST",
        url: "/auth/passwordRecovery",
        data: JSON.stringify(email),
        contentType: 'application/json',
        headers: getCSRFHeader(),
        success: function (data) {
            if (data != "ok") {
                $("#recSuccess").hide();
                document.getElementById("recErrorText").innerHTML = data;
                $("#recError").show();
            } else {
                $("#recError").hide();
                $("#recSuccess").show();
                document.getElementById("recButton").disabled = true;
            }
        },
        error: function (jqXHR, exception) {
            jsonErrorHandler(jqXHR, exception);
        }
    });
}

function getCSRFHeader() {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[csrfHeader] = csrfToken;
    return headers;
}