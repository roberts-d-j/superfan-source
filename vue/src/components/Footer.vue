<template>
  <footer class="footer">
    <div class="container">
      <p>
        &copy; {{ currentYear }} Superfan Source | Created by
        {{ teamNames.join(', ') }} | Project Manager: {{ projectManager }} |
        Owner: {{ owner }} | Made for Tech Elevator MMXXIII
      </p>
    </div>
  </footer>
</template>

<script>
export default {
  data() {
    return {
      currentYear: new Date().getFullYear(),
      teamNames: ['Dylan Roberts', 'DJ Litts', 'Tommy Bartley', 'Lindsey Haney', 'Christopher Hicks'],
      projectManager: 'Yoav Morahg',
      owner: 'Claribel Quintana',
    };
  },
};
</script>

<style scoped>
.footer {
  background-color: #f8f9fa;
  text-align: center;
  position: fixed;
  bottom: 0;
  width: 100%;
}
</style>
