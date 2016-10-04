
function constructTodoLists(data) {
    var result = document.getElementById("listResult");
    result.innerHTML = "";
    $.each(data, function(i, todoList) {
        var span = document.createElement("span");
        span.className = "badge";
        span.innerHTML = todoList.todoAmount;
        var button = document.createElement("button");
        button.id = "list=" + todoList.id;
        button.className = "list-group-item";
        button.onclick = loadTodos;
        button.name = todoList.name;
        button.innerHTML = todoList.name;
        button.appendChild(span);
        result.appendChild(button);
    });
}

function constructTodos(data) {
    var result = document.getElementById("todoResult");
    result.innerHTML = "";
    $.each(data, function(i, todo) {
        var signDiv = document.createElement("div");
        signDiv.style = "font-size:11px; text-align: right";
        var date = new Date(todo.created);
        signDiv.innerHTML = "Created by: " + todo.createdByName + ", at " + date.toLocaleTimeString() + " " +
            date.toLocaleDateString() + ".";
        var button = document.createElement("button");
        button.id = "todo=" + todo.id;
        button.className = "list-group-item";
        button.onclick = doneTodo;
        button.style = "word-wrap: break-word";
        button.innerHTML = todo.todoText;
        button.appendChild(signDiv);
        result.appendChild(button);
    });
}

function constructDoneTodos(data) {
    var result = document.getElementById("doneTodoResult");
    result.innerHTML = "";
    $.each(data, function(i, doneTodo) {
        var signDiv = document.createElement("div");
        signDiv.style = "font-size:11px; text-align: right";
        var date = new Date(doneTodo.created);
        var doneDate = new Date(doneTodo.done);
        signDiv.innerHTML = "Created by: " + doneTodo.createdByName + ", at " + date.toLocaleTimeString() + " " +
            date.toLocaleDateString() + ". Done by: " + doneTodo.doneByName + ", at " + doneDate.toLocaleTimeString() +
            " " + doneDate.toLocaleDateString() + ".";
        var button = document.createElement("button");
        button.id = "done=" + doneTodo.id;
        button.className = "list-group-item";
        button.onclick = unDoneTodo;
        button.style = "word-wrap: break-word; background-color: lightgrey";
        button.innerHTML = "<s>" + doneTodo.todoText + "</s>";
        button.appendChild(signDiv);
        result.appendChild(button);
    });
}

function constructDeleteInfo(data) {
    alert(JSON.stringify(data));
    var result = document.getElementById("delInfo");
    result.innerHTML = "";
    $.each(data, function(i, user) {
        var button = document.createElement("button");
        button.className = "list-group-item list-group-item-warning";
        button.style = "word-wrap: break-word";
        button.innerHTML = user.email + "<br>" + user.fName + " " + user.lName;
        button.disabled = true;
        result.appendChild(button);
    });
}

function constructShareInfo(data) {
    alert(JSON.stringify(data));
    var result = document.getElementById("sharedUsers");
    result.innerHTML = "";
    $.each(data, function(i, user) {
        var button = document.createElement("button");
        button.id = user.email;
        button.className = "list-group-item list-group-item-danger";
        button.style = "word-wrap: break-word";
        button.onclick = unShareUser;
        button.innerHTML = user.email + "<br>" + user.fName + " " + user.lName;
        //button.disabled = true;
        result.appendChild(button);
    });
}

function constructSearchResult(data) {
    alert(JSON.stringify(data));
    var result = document.getElementById("searchResult");
    result.innerHTML = "";
    $.each(data, function(i, todoList) {
        var group = document.createElement("div");
        group.className = "list-group";

        var listButton = document.createElement("button");
        listButton.id = "list=" + todoList.id;
        listButton.className = "btn btn-primary";
        listButton.onclick = loadTodos;
        listButton.style = "word-wrap: break-word";
        alert(todoList);
        listButton.innerHTML = todoList.name;
        group.appendChild(listButton);
        result.appendChild(group);

        //var button = document.createElement("button");
        //button.id = "list=" + todoList.id;
        //button.className = "list-group-item";
        //button.onclick = loadTodos;
        //button.name = todoList.name;
        //button.innerHTML = todoList.name;
        //button.appendChild(span);
        //result.appendChild(button);
    });
}