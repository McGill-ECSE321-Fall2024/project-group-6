<template>
	<main>
		<h1>{{ eventDetails ? eventDetails.name : "Event Not Found" }}</h1>
		<table>
			<tbody>
				<tr>
					<td>Date</td>
					<td>{{ eventDetails.date }} from {{ eventDetails.startTime }} to {{ eventDetails.endTime }}</td>
				</tr>
				<tr>
					<td>Registered</td>
					<td>{{ eventDetails.numRegistered }} (max {{ eventDetails.registrationLimit }})</td>
				</tr>
				<tr>
					<td>Location</td>
					<td>{{ eventDetails.type === "ONLINE" ? `Online at ${eventDetails.url}` : eventDetails.address }}</td>
				</tr>
			</tbody>
		</table>
	</main>
</template>

<script>
import axios from "axios";

const axiosClient = axios.create({
	baseURL: "http://localhost:8080"
});

export default {
	name: "event-details",
	data() {
		return {
			eventDetails: null
		};
	},
	async created() {
		// https://router.vuejs.org/guide/essentials/dynamic-matching.html
		this.fetchEventDetails(this.$route.params.eid);
	},
	methods: {
		async fetchEventDetails(eid) {
			try {
				const response = await axiosClient.get(`/events/${eid}`);
				this.eventDetails = response.data;
			}
			catch (e) {
				// TODO: Tell the user about it
				console.error(e);
			}
		}
	}
};
</script>

<style>

</style>
