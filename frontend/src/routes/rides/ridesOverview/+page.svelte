<script>
    import { page } from "$app/stores";
    import axios from "axios";
    import { onMount } from "svelte";
    import { jwt_token } from "../../../store";
  
   // const api_root = "https://502394bc-2ec7-4f62-9db1-9298f465d62d.mock.pstmn.io";
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
        url: api_root + "/api/rides/status/available",
        headers: {Authorization: "Bearer "+$jwt_token},
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
</script>


<h1>Alle verfügbaren Vermietungen</h1>
  <table class="table">
    <thead>
      <tr>
        <th scope="col">MotorradId</th>
        <th scope="col">Motorrad Details</th>
        <th scope="col">Land</th>
        <th scope="col">Stadt</th>
        <th scope="col">Start der Vermietung</th>
        <th scope="col">Ende der Vermietung</th>
        <th scope="col">Preis</th>
      </tr>
    </thead>
    <tbody>
      {#each rides as ride}
        <tr>
          
          <td>{ride.motorcycleId}</td>
          <td>
            <a href="/motorcycle/{ride.motorcycleId}">
              Details ansehen
            </a>
          </td>
          <td>{ride.pickupAdress.country}</td> <!-- Das Land ist im Objekt pickupAdress verschachtelt, komme ich so trotzdem ran? -->
          <td>{ride.pickupAdress.city}</td>
          <td>{ride.startingTime}</td>
          <td>{ride.endingTime}</td>
          <td>{ride.price}</td>
        </tr>
      {/each}
    </tbody>
  </table>
  
  <section class="cards">
    <div class="card">
      <h2>Vermietung hinzufügen</h2>
      <p>Füge eine Vermietung hinzu und profitiere von unserem Service.</p>
      <a class="primary-btn" href="/rides/ridesCreate">Vermietung hinzufügen</a>
    </div>
  </section>
