<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>index</title>
    <script src="https://cdn.bootcss.com/jquery/1.12.3/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/vue/2.4.4/vue.min.js"></script>
</head>
<body>
<div id="app">
    <input type="text" v-model="username" placeholder="username" />
    <button type="button" v-on:click="searchUser()">查询</button>
    <br>

    <input type="text" v-model="user.username" placeholder="username" />
    <input type="text" v-model="user.password" placeholder="password" />
    <input type="text" v-model="user.name" placeholder="name" />
    <button type="button" v-on:click="addUser()">添加</button>

    <table border="1">
        <thead>
        <tr>
            <th>id</th>
            <th>username</th>
            <th>name</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in users">
            <td>{{ user.id }}</td>
            <td>{{ user.username }}</td>
            <td><input type="text" v-model="user.name"/></td>
            <td>
                <a href="javascript:void(0)" v-on:click="deleteUser(user.id)">删除</a>
                <a href="javascript:void(0)" v-on:click="updateUser(user.id, user.name)">修改</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<script>
    var vueData = {
        users: [],
        username: '',
        user: {}
    };

    new Vue({
        el: '#app',
        data: vueData,
        methods: {
            searchUser: function () {
                $.get('/listUser?username=' + vueData.username, function (data) {
                    if (data.success) {
                        vueData.users = data.list;
                    } else {
                        alert('查询失败');
                    }
                });
            },
            deleteUser: function (id) {
                var _this = this;
                $.post('/deleteUser', {id: id}, function (data) {
                    if (data.success) {
                        _this.searchUser();
                        alert('删除成功');
                    } else {
                        alert('删除失败');
                    }
                });
            },
            updateUser: function (id, name) {
                var _this = this;
                $.post('/updateUser', {id: id, name: name}, function (data) {
                    if (data.success) {
                        _this.searchUser();
                        alert('修改成功');
                    } else {
                        alert('修改失败');
                    }
                });
            },
            addUser: function () {
                var _this = this;
                $.ajax({
                    type: "POST",
                    url: "/addUser",
                    data: JSON.stringify(vueData.user),
                    contentType: 'application/json',
                    dataType: 'JSON',
                    success: function (data) {
                        if (data.success) {
                            _this.searchUser();
                            alert('添加成功');
                        } else {
                            alert('添加失败');
                        }
                    }
                });
            }
        },
        mounted: function () {
            this.$nextTick(function () {
                this.searchUser();
            })
        }
    });
</script>
</body>
</html>
