<template id="app">
  <div>
    <div class="app-font">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  
};
</script>

<style>
body {
  margin: 0px;
}

.body {
  display: grid;
  align-items: center;
  justify-content: center;
  grid-template-columns: 1fr 2fr 1fr;
  gap: 2px;
}

/* Scroll bar stylings */
::-webkit-scrollbar {
    width: 20px;
    height: 100%;
  }

  /* Track */
  ::-webkit-scrollbar-track {
    background: var(--grey); 
  }
  
  /* Handle */
  ::-webkit-scrollbar-thumb {
    background: rgba(73, 218, 198, 0.8);
    border-radius: 7.5px;
  }

  /* Handle on hover */
  ::-webkit-scrollbar-thumb:hover {
    background: rgb(172, 16, 102, 0.8);
    
  }

#app,
.app-font {
  font-family: Arial, Helvetica, sans-serif;
}

button {
  margin-bottom: 20px;
  height: 50px;
  background-color: steelblue;
  color: rgb(255, 255, 255);
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
  border-style: outset;
  border-color: oldlace;
  
  font-size: 13pt;
  border-radius: 20px;
  font-weight: bolder;
  text-transform: uppercase;
}

.select-file-button {
  margin-bottom: 20px;
  background-color: steelblue;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  font-size: 15pt;
  border-radius: 20px;
  width: 120px;
  border: 5px;
  border-style: outset;
  border-color: oldlace;
  text-align: center;
  font-weight: bolder;
  color: white;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.followLabel {
  margin-bottom: 20px;
  margin-top: 20px;
  background-color: steelblue;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  font-size: 15pt;
  border-radius: 20px;
  width: 120px;
  border: 5px;
  border-style: outset;
  border-color: oldlace;
  text-align: center;
  font-weight: bolder;
}


</style>

