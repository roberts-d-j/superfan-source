<template>
    <div class="background">
    <!-- background image is stored  -->
    <title-bar class="header" />
    <sidebar class="sidebar" />
    <message-form class="content" />
  </div>
</template>

<script>
import MessageForm from "../components/MessageForm.vue";
import TitleBar from "../components/TitleBar.vue";
import Sidebar from "../components/sidebar.vue";
export default {
    components: { MessageForm, TitleBar, Sidebar }
    
}
</script>

<style>
.background {
  background: url("../images/video-store.jpg");
  background-size: cover;
  background-position: center;
  position: fixed;
  height: 100vh;
  width: 100vw;
  display: grid;
  grid-template-columns: 300px 1fr 100px;
  grid-template-rows: 100px 1fr 50px;
  grid-template-areas:
    "header header header"
    "navigation content . "
    ". . .";
  gap: 40px;
  
  
}

.header {
    grid-area: header;
}

.sidebar {
    grid-area: navigation;
}
.content {
    grid-area: content;
}
</style>