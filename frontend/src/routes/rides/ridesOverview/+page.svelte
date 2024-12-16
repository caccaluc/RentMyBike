<script>
    import { page } from "$app/stores";
    import axios from "axios";
    import { onMount } from "svelte";
    import { jwt_token } from "../../../store";
  
   // const api_root = "https://502394bc-2ec7-4f62-9db1-9298f465d62d.mock.pstmn.io";
   const api_root = $page.url.origin;

  let currentPage;
  let nrOfPages = 0;
  let defaultPageSize = 6;

  let priceMin;
  let priceMax;
  let startTime;
  let endTime;
  let city;

  
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

$: {
    if ($jwt_token !== "") {
      let searchParams = $page.url.searchParams;

      if (searchParams.has("page")) {
        currentPage = searchParams.get("page");
      } else {
        currentPage = "1";
      }
      getRides();
    }
  }
 
 /* 
    onMount(() => {
      getRides();
    });*/
  
    function getRides() {
      let query = "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;

      if (priceMin) {
        query += "&minPrice=" + priceMin;
      }
      if (priceMax) {
        query += "&maxPrice=" + priceMax;
      }
      if (city) {
        query += "&city=" + city;
      }
      if (startTime) {
        query += "&startTime=" + startTime;
      }
      if (endTime) {
        query += "&endTime=" + endTime;
      }


      var config = {
        method: "get",
        url: api_root + "/api/rides/status/available" + query,
        headers: {Authorization: "Bearer "+$jwt_token},
      };
  
      axios(config)
        .then(function (response) {
          rides= response.data.content;

          nrOfPages = response.data.totalPages;
        })
        .catch(function (error) {
          alert("Could not get rides");
          console.log(error);
        });
    }
</script>


<h1>Alle verfügbaren Vermietungen</h1>

<div class="row my-3">
  <div class="col-auto">
    <label for="" class="col-form-label">mind. Preis: </label>
  </div>
  <div class="col-3">
    <input
      class="form-control"
      type="number"
      id="priceMinfilter"
      placeholder="min"
      bind:value={priceMin}
    />
  </div>
  <div class="col-auto">
    <label for="" class="col-form-label">max. Preis: </label>
  </div>
  <div class="col-3">
    <input
      class="form-control"
      type="number"
      id="priceMaxfilter"
      placeholder="max"
      bind:value={priceMax}
    />
  </div>
  <div class="col-auto">
    <label for="" class="col-form-label">Stadt: </label>
  </div>
  <div class="col-3">
    <input
      class="form-control"
      type="text"
      id="cityfilter"
      placeholder="Stadt"
      bind:value={city}
    />
  </div>
</div>
<div class="row my-3">
  <div class="col-auto">
    <label for="" class="col-form-label">Datum ab: </label>
  </div>
  <div class="col-3">
    <input
      class="form-control"
      type="datetime-local"
      id="startTimefilter"
      placeholder="Datum ab"
      bind:value={startTime}
    />
  </div>
  <div class="col-auto">
    <label for="" class="col-form-label">Datum bis: </label>
  </div>
  <div class="col-3">
    <input
      class="form-control"
      type="datetime-local"
      id="endTimefilter"
      placeholder="Datum bis"
      bind:value={endTime}
    />
  </div>
  

  <div class="col-3">
    <a
      class="btn btn-primary"
      href={"/rides/ridesOverview?page=1&minPrice=" + priceMin + "&maxPrice=" + priceMax + "&city=" + city + "&startTime=" + startTime + "&endTime=" + endTime}
      role="button">Filtern</a
    >
  </div>
</div>
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

  <nav>
    <ul class="pagination">
      {#each Array(nrOfPages) as _, i}
        <li class="page-item">
          <a
            class="page-link"
            class:active={currentPage == i + 1}
            href={"/rides/ridesOverview?page=" + (i + 1)}
            >{i + 1}
          </a>
        </li>
      {/each}
    </ul>
  </nav>
  
  <section class="cards">
    <div class="card">
      <h2>Vermietung hinzufügen</h2>
      <p>Füge eine Vermietung hinzu und profitiere von unserem Service.</p>
      <a class="primary-btn" href="/rides/ridesCreate">Vermietung hinzufügen</a>
    </div>
  </section>


  
