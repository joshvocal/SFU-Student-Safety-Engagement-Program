<template>
    <div id="userlist">
        <v-layout row wrap id="buttons">
            <v-flex xs7>
                <v-select   id="dropdown"
                            v-bind:items="teamList"
                            v-model="selectedTeam"
                            @input="requestUsers"></v-select>
            </v-flex>
            <!-- <span>Selected: {{ team }}</span> -->
            <v-flex xs5>
                <add-user   id="add-user"
                            @add="onClickAdd"
                            v-bind:teams="teams"
                            v-bind:roles='roles'></add-user>
            </v-flex>
            <v-flex xs12>
                <ul id="listedItems">
                    <listed-user    v-for="user in users"
                                    v-bind:user="user"></listed-user>
                </ul>
            </v-flex>
        </v-layout>



</div>
</template>

<script>
import AddUser from "./AddUser.vue";
import ListedUser from "./ListedUser.vue";
import axios from 'axios';

export default {
    name: 'scroll',
    data() {
        return {
            users: [],
            teamIDs: [],
            selectedTeam: -1,
            all: {
                id: -1,
                name: "all teams",
            },
        }
    },
    methods: {
        onClickAdd(data) {
            this.teamIDs = data.teamIDs;

            let user = data.user;
            axios.post('/api/users', user)
                .then(response => this.updateAfterAddingUser(user))
                .catch(function (error) {
                    console.log(error);
                });

        },
        populateUsers(response) {
            this.users = response.data;
        },
        updateAfterAddingUser(user) {
            axios.get('/api/users/email/' + user.sfuEmail)
                .then(this.assignUserToTeams)
                .catch(function (error) {
                    console.log(error);
                });
        },
        assignUserToTeams(response) {
            let user = response.data;
            axios.post('/api/users/' + user.id + '/teams',
                        {teamIdList: this.teamIDs})
                .then(response => this.requestUsers())
                .catch(function (error) {
                    console.log(error);
                });
        },
        requestUsers() {
            var usersURL = '';
            if (this.selectedTeam > 0) {
                usersURL = '/api/users/teams/' + this.selectedTeam;
            } else {
                usersURL = '/api/users';
            }

            axios.get(usersURL)
            .then(this.populateUsers)
            .catch(function (error) {
                console.log(error);
            });
        }
    },
    components: {
        "listed-user": ListedUser,
        "add-user": AddUser
    },
    created: function () {
        this.requestUsers();
    },
    props: {
        teams: {
            type: Array,
            required: true
        },
        roles: {
            type: Array,
            required: true
        },
    },
    computed: {
        teamList: function () {
            let list = [this.all].concat(this.teams);
            return list.map(function (team) {
                let item = {};
                item.value = team.id;
                item.text = team.name;
                return item;
            });
        }
    },
};


</script>

<style lang="scss" scoped>
#listedItems {
    overflow-x: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    max-width: 100%;
    text-align: left;
    font-size: 1.5em;
    max-height: 100%;
    padding: 0;
    flex-flow: column;

}

#buttons {
    padding: .5em;
    display: flex;
    width: 100%;
    height: 2em;
}

#userlist{
    background: #FBFBFB;
}

</style>
