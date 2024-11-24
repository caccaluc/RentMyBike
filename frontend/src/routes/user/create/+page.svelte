<script>
    import axios from "axios";
    import { page } from "$app/stores";
    import { onMount } from "svelte";
  
    const api_root = $page.url.origin;
  
    let users = [];
    let user = {
        firstName: null, 
        lastName: null, 
        email: null,
        licenseCode: null, 
        phoneNumber: null,
        adress: {
          street: null,
          streetNumber: null,
          postalCode: null,
          city: null,
          country: null,
        },
        birthdate: null,
    };
  
    onMount(() => {
      getUsers();
    });
  
    function getUsers() {
      var config = {
        method: "get",
        url: api_root + "/api/users/all",
        headers: {},
      };
  
      axios(config)
        .then(function (response) {
          users = response.data;
        })
        .catch(function (error) {
          alert("Could not get users");
          console.log(error);
        });
    }
  
    function createUser() {
      var config = {
        method: "post",
        url: api_root + "/api/users/create",
        headers: {
          "Content-Type": "application/json",
        },
        data: user,
      };
  
      axios(config)
        .then(function (response) {
          alert("User created");
          getUsers();
        })
        .catch(function (error) {
          alert("Could not create User");
          console.log(error);
        });
    }
  </script>
  
  <h1 class="mt-3">Benutzer erstellen</h1>
  <form class="mb-5">
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="name">Vorname</label>
        <input
          bind:value={user.firstName}
          class="form-control"
          id="name"
          type="text"
        />
      </div>
    </div>
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="lastName">Nachname</label>
        <input
          bind:value={user.lastName}
          class="form-control"
          id="lastName"
          type="text"
        />
      </div>
    </div>
    <div class="row mb-3">
        <div class="col">
          <label class="form-label" for="email">E-Mail</label>
          <input
            bind:value={user.email}
            class="form-control"
            id="email"
            type="email"
          />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <label class="form-label" for="licensCode">Führerschein-ID</label>
          <input
            bind:value={user.licenseCode}
            class="form-control"
            id="licenseCode"
            type="text"
          />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <label class="form-label" for="phoneNumber">Telefonnummer</label>
          <input
            bind:value={user.phoneNumber}
            class="form-control"
            id="phoneNumber"
            type="number"
          />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <label class="form-label" for="birthdate">Geburtsdatum</label>
          <input
            bind:value={user.birthdate}
            class="form-control"
            id="birthdate"
            type="date"
          />
        </div>
      </div>
      <h3>Adresse</h3>
      <div class="row mb-3">
        <div class="col">
          <label class="form-label" for="street">Strasse</label>
          <input
            bind:value={user.adress.street}
            class="form-control"
            id="street"
            type="text"
          />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <label class="form-label" for="streetNumber">Hausnummer</label>
          <input
            bind:value={user.adress.streetNumber}
            class="form-control"
            id="streetNumber"
            type="number"
          />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <label class="form-label" for="postalCode">PLZ</label>
          <input
            bind:value={user.adress.postalCode}
            class="form-control"
            id="postalCode"
            type="number"
          />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <label class="form-label" for="city">Stadt</label>
          <input
            bind:value={user.adress.city}
            class="form-control"
            id="city"
            type="text"
          />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <label class="form-label" for="country">Land</label>
          <input
            bind:value={user.adress.country}
            class="form-control"
            id="country"
            type="text"
          />
        </div>
    </div>
      
    <button type="button" class="btn btn-primary" on:click={createUser}>Erstellen</button
    >
  </form>
  
  
  