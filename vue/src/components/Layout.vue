<template>
  <div :class="layoutClass" :style="backgroundStyle">
    <router-view />
  </div>
</template>

<script>
export default {
  computed: {
    layoutClass() {
      const route = this.$route;
      return route.meta.layoutClass || ""; /* Default to an empty string if no class is specified */
    },
    backgroundStyle() {
      const route = this.$route;
      const backgroundImage = route.meta.backgroundImage || "";
      return backgroundImage ? `background-image: url(${require("../images/" + backgroundImage)})` : "";
    },
  },
};
</script>

<style scoped>
#titleBar {
  display: flex;
  justify-content: left;
  align-items: center;
  background-color: rgb(252, 141, 6, 0.25);
  padding: 10px;
  gap: 50px;
}

/* CSS styles for the sidebar go here */

.default {
  color: gray;
}

.body {
  display: grid;
  align-items: center;
  justify-content: center;
  grid-template-columns: 1fr 2fr 1fr;
  gap: 2px;
}

#app {
  font-family: Verdana, Geneva, Tahoma, sans-serif;
}

.hiphop {
  background-size: cover;
  background-position: center;
  min-height: 100vh;
}
</style>
