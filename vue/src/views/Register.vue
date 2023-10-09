<template>
  <div id="register" class="text-center">
    <div class="register-background"></div>

    <!-- Create a separate div for background -->
    <div id="registerContent">
      <div class="headerBar">
        <!-- <h1 id="header">SUPERFAN SOURCE</h1> -->
        <img id="headerImg" src="../images/superfan-source.png" />
      </div>
      <form @submit.prevent="register" class="register-box">
        <h1 class="register-title">Create Account</h1>
        <div role="alert" v-if="registrationErrors">
          {{ registrationErrorMsg }}
        </div>
        <div class="form-input-group">
          <label for="username">Username:</label>
          <input type="text" id="username" v-model="user.username" required autofocus />
        </div>
        <div class="form-input-group">
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="user.password" required />
        </div>
        <div class="form-input-group">
          <label for="confirmPassword">Confirm Password:</label>
          <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
        </div>
        <button type="submit" class="register-button">Create Account</button>
        <p>
          <router-link :to="{ name: 'login' }">Already have an account? Log in.</router-link>
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
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "user",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors.";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
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

.register-background {
  position: absolute;
  width: 100%;
  height: 100%;
  background: url("../images/register-background.jpg"); /* Set the background image */
  background-size: cover;
  background-position: center;
  z-index: -1; /* Send the background behind the content */
}

.register-box {
  background-color: rgb(67, 235, 212, 0.8); /* Black background color with opacity */
  color: rgb(235, 39, 137); /* Hot pink text color */
  border-radius: 20px;
  padding: 20px;
  margin-bottom: 50px;
  max-width: 400px;
  max-height: 500px;
  text-align: center;
   text-shadow: -1px 0 rgb(255, 255, 255), 0 1px rgb(255, 255, 255), 1px 0 rgb(255, 255, 255), 0 -1px rgb(255, 255, 255);
}

.register-button {
  background-color: rgb(223, 42, 132); /* Hot pink button background color */
  color: rgb(255, 255, 255); /* Black button text color */
  border: none;
  padding: 10px 20px;
  border-radius: 15px;
  cursor: pointer;
  margin-top: 10px;
  border-style: outset;
  border-color: rgb(0, 0, 0);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.form-input-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-right: 1rem;
}

.headerBar {
  border-radius: 20px;
  margin-bottom: 20px; 
  background:rgba(102, 51, 153, .9);
}

#headerImg {
  height: 300px;
}
#header {
  font-family: Impact, Haettenschweiler, "Arial Narrow Bold", sans-serif;
  font-style: italic;
  font-size: 50px;
}

#registerContent {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.footer {
  background-color: rgba(248, 249, 250, 0.0);
  text-shadow: -1px 0 rgb(255, 255, 255), 0 1px rgb(255, 255, 255), 1px 0 rgb(255, 255, 255), 0 -1px rgb(255, 255, 255);
  color: rgb(223, 42, 132);
  text-align: center;
  position: fixed;
  margin-top: 20px;
  width: 100%;
  height: 60px;
}
</style>
