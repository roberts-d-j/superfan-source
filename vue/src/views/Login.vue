<template>
  <div id="pageContent" class="text-center">
    <div class="login-background"></div>

    <!-- Create a separate div for background -->
    <div id="loginContainer">
      <div class="headerBar">
        <!-- <h1 id="header">SUPERFAN SOURCE</h1> -->
        <img id="headerImg" src="../images/superfan-source.png" />
      </div>
      <form @submit.prevent="login" class="login-box">
          <h1 class="login-title">Please Sign In</h1>
          <div role="alert" v-if="invalidCredentials">
            Invalid username and password!
          </div>
          <div role="alert" v-if="this.$route.query.registration">
            Thank you for registering, please sign in.
          </div>
          <div class="form-input-group">
            <label for="username">Username:</label>
            <input type="text" id="username" v-model="user.username" required autofocus />
          </div>
          <div class="form-input-group">
            <label for="password">Password:</label>
            <input type="password" id="password" v-model="user.password" required />
          </div>
          <button type="submit" class="login-button">Sign In</button>
          <p>
            <router-link :to="{ name: 'register' }">Need an account? Sign up.</router-link>
          </p>
      </form>
    </div>
    <Footer/>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import Footer from "../components/Footer.vue";
export default {
  components: { Footer },
  name: "login",
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style scoped>
.text-center {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  position: relative; /* Add position relative to create a stacking context */
}

.login-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url("../images/login-background-2.jpg"); 
  background-size: cover;
  background-position: center;
  opacity: 0.9; /* Set the opacity for the background */
  z-index: -1; /* Send the background behind the content */
}

.login-box {
  background-color: rgb(166, 228, 43, 0.9); 
  color: rgb(235, 39, 137); 
  border-radius: 20px;
  margin-bottom: 30px;
  padding: 20px;
  max-width: 400px;
  text-align: center;
   text-shadow: -1px 0 rgb(255, 255, 255), 0 1px rgb(255, 255, 255), 1px 0 rgb(255, 255, 255), 0 -1px rgb(255, 255, 255);
}

.login-button {
  background-color: rgb(245, 150, 61); 
  color: rgb(0, 0, 0); 
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px;
  border-radius: 15px;
  border-style: outset;
  border-color: rgb(0, 0, 0);
  text-shadow: -1px 0 rgb(255, 255, 255), 0 1px rgb(255, 255, 255), 1px 0 rgb(255, 255, 255), 0 -1px rgb(255, 255, 255);
}

.form-input-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-right: 1rem;
}

#header {
  font-family: Impact, Haettenschweiler, "Arial Narrow Bold", sans-serif;
  font-style: italic;
  font-size: 50px;
}

.headerBar {
  border-radius: 20px;
  margin-bottom: 20px; 
  background: rgba(102, 51, 153, .9);
}

#headerImg {
  height: 300px;
}

#loginContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.footer {
  background-color: rgba(248, 249, 250, 0.0);
  text-shadow: -1px 0 rgb(223, 42, 132), 0 1px rgb(223, 42, 132), 1px 0 rgb(223, 42, 132), 0 -1px rgb(223, 42, 132);
  color: white;
  text-align: center;
  position: fixed;
  margin-top: 20px;
  width: 100%;
  height: 60px;
}
</style>
