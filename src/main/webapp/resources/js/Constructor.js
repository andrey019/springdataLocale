
function makeTodoLists(data) {
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

function makeTodos(data) {
    var result = document.getElementById("todoResult");
    result.innerHTML = "";
    $.each(data, function(i, todo) {
        var signDiv = document.createElement("div");
        signDiv.style = "font-size:11px; text-align: right";
        signDiv.innerHTML = "Created by: " + todo.createdByName + ", " + todo.created + ".";
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
