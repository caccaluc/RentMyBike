<script>
    import { page } from "$app/stores";
    import axios from "axios";
    import { onMount } from "svelte";
    import { jwt_token } from "../../../store";
  
    const api_root = $page.url.origin;

    let motorcycles = [];
    let motorcycle = {
        brand: null,
        model: null,
        year: null,
        value: null,
        color: null,
        licensePlate: null,
        ps: null,
        km: null,  
    };
    
    function createMotorcycle() {
        
      var config = {
        method: "post",
        url: api_root + "/api/motorcycles/create",
        headers: {
          "Content-Type": "application/json",
            Authorization: "Bearer "+$jwt_token,
        },
        data: motorcycle,
      };
  
      axios(config)
        .then(function (response) {
          alert("Motorcycle created");
          console.log(response.data);
          getMotorcycles();
        })
        .catch(function (error) {
          alert("Could not create Motorcycle");
          console.log(error);
        });
    }

</script>


<h1 class="mt-3">Motorrad erstellen</h1>
<form class="mb-5">
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="brand">Marke</label>
            <input
                bind:value={motorcycle.brand}
                class="form-control"
                id="brand"
                type="text"
            />
        </div>
    </div>
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="model">Modell</label>
            <input
                bind:value={motorcycle.model}
                class="form-control"
                id="model"
                type="text"
            />
        </div>
    </div>
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="year">Baujahr</label>
            <input
                bind:value={motorcycle.year}
                class="form-control"
                id="year"
                type="number"
            />
        </div>
    </div>
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="value">Wert</label>
            <input
                bind:value={motorcycle.value}
                class="form-control"
                id="value"
                type="number"
            />
        </div>
    </div>
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="color">Farbe</label>
            <input
                bind:value={motorcycle.color}
                class="form-control"
                id="color"
                type="text"
            />
        </div>
    </div>
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="licensePlate">Kennzeichen</label>
            <input
                bind:value={motorcycle.licensePlate}
                class="form-control"
                id="licensePlate"
                type="text"
            />
        </div>
    </div>
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="ps">PS</label>
            <input
                bind:value={motorcycle.ps}
                class="form-control"
                id="ps"
                type="number"
            />
        </div>
    </div>
    <div class="row mb-3">
        <div class="col">
            <label class="form-label" for="km">Kilometerstand</label>
            <input
                bind:value={motorcycle.km}
                class="form-control"
                id="km"
                type="number"
            />
        </div>
    </div>
    <button type="button" class="btn btn-primary" on:click={createMotorcycle}>Erstellen</button>
</form>
  