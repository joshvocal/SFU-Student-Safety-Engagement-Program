<template>

    <v-container>

        <ul v-if="scheduleReminders.length > 0" class="my-feed">
            <li class="my-feed-element" v-for="scheduleReminder in scheduleReminders">
                <ScheduleReminder
                        v-bind:shiftName="scheduleReminder.shiftName"
                        v-bind:shiftStartTime="scheduleReminder.shiftStartTime"
                        v-bind:shiftEndTime="scheduleReminder.shiftEndTime"
                        v-bind:shiftTeamName="scheduleReminder.shiftTeamName"
                        v-bind:shiftDate="scheduleReminder.shiftDate"
                        v-bind:daysTilShift="scheduleReminder.daysTilShift">
                </ScheduleReminder>
            </li>
        </ul>

        <h2 v-if="scheduleReminders.length <= 0" class="empty-message" display-3>No Notifications</h2>
        <h6 v-if="scheduleReminders.length <= 0" class="empty-message" title>You have no upcoming events this week.</h6>

    </v-container>

</template>

<script>

    import ScheduleReminder from './ScheduleReminder.vue'
    import axios from 'axios';

    export default {
        name: 'my-feed',
        data() {
            return {
                scheduleReminders: [],
                loggedInUserId: this.$store.getters.id
            }
        },
        components: {
            ScheduleReminder
        },
        methods: {
            populateScheduleReminder(scheduleReminder) {
                var arrLength = scheduleReminder.data.length;

                for (var i = 0; i < arrLength; i++) {
                    this.scheduleReminders.splice(i, 0, scheduleReminder.data[i])
                }
            }
        },
        created: function () {
            this.loggedInUserId = this.$store.getters.uid;
            axios.get('/api/users/' + this.loggedInUserId + '/reminders')
                .then(this.populateScheduleReminder)
                .catch(function (error) {
                    console.log(error)
                });
        }
    }

</script>

<style scoped>

    html, body {
        height: 100%;
    }

    .empty-message {
        text-align: center;
    }

    .container {
        padding: 0;
    }

    .my-feed {
        height: 90vh;
        overflow-y: scroll;
        padding: 0;
    }

    .my-feed-element {
        list-style: none;
        padding: 1em;
    }

</style>
