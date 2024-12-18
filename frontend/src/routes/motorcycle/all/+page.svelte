<script>
    import { page } from "$app/stores";
    import axios from "axios";
    import { onMount } from "svelte";
    import { jwt_token } from "../../../store";
  
  
    const api_root = $page.url.origin;
  
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
            console.log("Motorcycles Response:", response.data);
            motorcycles= response.data;
          })
          .catch(function (error) {
            alert("Could not get motorcycles");
            console.log(error);
          });
      }
  
  
  </script>
  
  
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
  
  
    
