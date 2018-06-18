package com.example.knoxpo.todolistfirestore.Model

class ToDoCollection {

    companion object {

        const val NAME = "TodoList"
    }

    class Fields {

        companion object {

            const val USERID = "userId"
            const val TITLE = "title"
            const val CHECKED = "isChecked"
            const val SHAREID = "shareId"
            const val SHARES = "shares"
        }
    }
}