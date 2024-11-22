<script>
    import axios from "axios";
    import { onMount } from "svelte";
  
    const api_root = "https://xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.mock.pstmn.io";
  
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
</script>


<h1>All Rides</h1>
  <table class="table">
    <thead>
      <tr>
        <th scope="col">MotorradId</th>
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
          <td>{ride.motorcycleId}</td> <!-- hier will ich nicht die ID anzeigen, sondern Infos vom Motorrad -->
          <td>{ride.pickupAdress.country}</td> <!-- Das Land ist im Objekt pickupAdress verschachtelt, komme ich so trotzdem ran? -->
          <td>{ride.pickupAdress.city}</td>
          <td>{ride.startingTime}</td>
          <td>{ride.endingTime}</td>
          <td>{ride.price}</td>
        </tr>
      {/each}
    </tbody>
  </table>
  