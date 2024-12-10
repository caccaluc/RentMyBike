<script>
  import { page } from "$app/stores";
  import axios from "axios";
  import { onMount } from "svelte";
  import { jwt_token } from "../../../store";


  const api_root = $page.url.origin;

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
      headers: {Authorization: "Bearer "+$jwt_token},
    };

    axios(config)
      .then(function (response) {
        rides = response.data;
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
        Authorization: "Bearer "+$jwt_token,
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

  /** Definition für Motorräder */
 


  let motorcycles = [];
    let motorcycle = {
        brand: null,
        model: null, 
        year: null,
        color: null, 
        licenceplate: null, 
        value: null,
        ps: null,
        km: null,
        UserId: null,
      
    };
    
    onMount(() => {
      getMotorcycles();
    });
  
    function getMotorcycles() {
      var config = {
        method: "get",
        url: api_root + "/api/motorcycles",
        headers: {Authorization: "Bearer "+$jwt_token},
      };
  
      axios(config)
        .then(function (response) {
          motorcycles= response.data;
        })
        .catch(function (error) {
          alert("Could not get motorcycles");
          console.log(error);
        });
    }


</script>

<h1 class="mt-3">Erstelle eine Vermietung</h1>
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
    <br>
    <button type="button" class="btn btn-primary" on:click={createRide}
      >Hinzufügen</button
    >
  </div>
</form>

<h1>Alle Motorräder</h1>

<table class="table">
  <thead>
    <tr>
      <th scope="col">Besitzer (User)</th>
      <th scope="col">Marke</th>
      <th scope="col">Modell</th>
      <th scope="col">Jahr</th>
      <th scope="col">PS</th>
      <th scope="col">Kilometerstand</th>
      <th scope="col">Farbe</th>
      <th scope="col">Wert</th>
    </tr>
  </thead>
  <tbody>
    {#each motorcycles as motorcycle}
      <tr>
        <td>{motorcycle.userId}</td>
        <td>{motorcycle.brand}</td>
        <td>{motorcycle.model}</td>
        <td>{motorcycle.year}</td>
        <td>{motorcycle.ps}</td>
        <td>{motorcycle.km}</td>
        <td>{motorcycle.color}</td>
        <td>{motorcycle.value}</td>
      </tr>
    {/each}
  </tbody>
</table>


  
  <section class="cards">
    <div class="card">
      <h2>Motorrad hinzufügen</h2>
      <p>Füge ein Mottorrad hinzu, um eine Vermietung zu erstellen.</p>
      <a class="primary-btn" href="/rides/ridesCreate">Motorrad hinzufügen</a>
    </div>
  </section>
