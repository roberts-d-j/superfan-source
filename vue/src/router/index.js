import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import NewBand from '../views/NewBand.vue'
import BandView from '../views/BandView.vue'
import SearchPage from '../views/SearchPage.vue'
import ManagedBand from '../views/ManagedBand.vue'
import PhotoGallery from '../views/PhotoGallery.vue'
import InboxView from '../views/InboxView.vue'
import UpdateBand from '../views/UpdateBand.vue'
import SendMessage from '../views/SendMessage.vue'
Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false,
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false,
      }
    },
    {
      path: "/newband",
      name: "newBand",
      component: NewBand,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/bands/:bandName",
      name: "bandPageView",
      component: BandView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/bands/search/:searchTerms",
      name: "search",
      component: SearchPage,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/bands/ownedbands/:userId",
      name: "managed",
      component: ManagedBand,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/bands/:bandId/gallery",
      name: "gallery",
      component: PhotoGallery,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/messages",
      name: "inbox",
      component: InboxView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/bands/update/:bandId",
      name: "updateBand",
      component: UpdateBand,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/messages/newMessage/:bandId",
      name: "newMessage",
      component: SendMessage,
      meta: {
        requiresAuth: true
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
