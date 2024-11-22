<script>
    import axios from "axios";
    import { onMount } from "svelte";
  
    const api_root = "https://502394bc-2ec7-4f62-9db1-9298f465d62d.mock.pstmn.io";
  
    let rides = [];
    let ride = {
        motorcycleId: null,
        country: null, 
        city: null,
        postalCode: null, 
        street: null, 
        streetNumber: null,
        startingTime: null,
        endingTime: null,
        price: null,
      
    };
  
    onMount(() => {
      getRides();
    });
  
    function getRides() {
      var config = {
        method: "get",
        url: api_root + "/api/rides/status/AVAILABLE",
        headers: {},
      };
  
      axios(config)
        .then(function (response) {
          rides= response.data;
        })
        .catch(function (error) {
          alert("Could not get rides");
          console.log(error);
        });
    }
  
    function createRide() {
      var config = {
        method: "post",
        url: api_root + "/api/rides/create",
        headers: {
          "Content-Type": "application/json",
        },
        data: ride,
      };
  
      axios(config)
        .then(function (response) {
          alert("Ride created");
          getRides();
        })
        .catch(function (error) {
          alert("Could not create Ride");
          console.log(error);
        });
    }
  </script>
  
  <h1 class="mt-3">Create Ride</h1>
  <form class="mb-5">
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="motorcycleId">Motorrad-ID</label>
        <input
          bind:value={ride.motorcycleId}
          class="form-control"
          id="motorcycleId"
          type="text"
        />
      </div>
    </div>
    <div class="row mb-3">
      <div class="col">
        <label class="form-label" for="country">Land</label>
        <input
          bind:value={ride.country}
          class="form-control"
          id="country"
          type="text"
        />
    </div>
    <div class="col">
        <label class="form-label" for="city">Stadt</label>
        <input
          bind:value={ride.city}
          class="form-control"
          id="city"
          type="text"
        />
    </div>
    <div class="col">
        <label class="form-label" for="postalCode">PLZ</label>
        <input
          bind:value={ride.postalCode}
          class="form-control"
          id="postalCode"
          type="number"
        />
    </div>
    <div class="col">
        <label class="form-label" for="street">Strasse</label>
        <input
          bind:value={ride.street}
          class="form-control"
          id="street"
          type="text"
        />
    </div>
    <div class="col">
        <label class="form-label" for="streetNumber">Nummer</label>
        <input
          bind:value={ride.streetNumber}
          class="form-control"
          id="streetNumber"
          type="number"
        />
    </div>
    <div class="col">
        <label class="form-label" for="startingTime">Start der Vermietung</label>
        <input
          bind:value={ride.startingTime}
          class="form-control"
          id="startingTime"
          type="datetime-local"
        />
    </div>
    <div class="col">
        <label class="form-label" for="endingTime">Ende der Vermietung</label>
        <input
            bind:value={ride.endingTime}
            class="form-control"
            id="endingTime"
            type="datetime-local"
            />
    </div>
    <div class="col">
        <label class="form-label" for="price">Preis</label>
        <input
            bind:value={ride.price}
            class="form-control"
            id="price"
            type="number"
            />
    </div>
    <button type="button" class="btn btn-primary" on:click={createRide}
      >Hinzufügen</button
    >
  </form>
  
  