<script>
    import { page } from "$app/stores";
    import axios from "axios";
    import { onMount } from "svelte";
    import { jwt_token } from "../../../store";
    import { isAuthenticated, user as storeUser } from "../../../store";

    const api_root = $page.url.origin;

    let rentals = []; // Array für die Rentals
    let defaultPageSize = 3; // Standard-Größe für die Pagination
    let nrOfPages = 0; // Anzahl der Seiten für Pagination
    let currentPage; // Standard-Sei

    // Variablen für den eingeloggten User
    let user = {
        id: null,
        state: null,
        firstName: null,
        lastName: null,
        email: null,
        licenceCode: null,
        birthdate: null,
        phoneNumber: null,
        averageRating: null,
        numberOfRatings: null,
        adress: null,
        ratings: null,
    };

    $: {
        if ($jwt_token !== "") {
            let searchParams = $page.url.searchParams;

            if (searchParams.has("page")) {
                currentPage = searchParams.get("page");
            } else {
                currentPage = "1";
            }
            getMyRentals();
        }
    }

    /* 
      onMount(() => {
        getRides();
      });*/

    // Funktion, um die Rides zu bekommen
    function getMyRentals() {
        let query =
            "?pageSize=" + defaultPageSize + "&pageNumber=" + currentPage;
        var config = {
            method: "get",
            url: api_root + "/api/me/rentals" + query,
            headers: { Authorization: "Bearer " + $jwt_token },
        };

        axios(config)
            .then(function (response) {
                rentals = response.data.content; // Speichern der Vermietungen
                nrOfPages = response.data.totalPages; // Anzahl der Seiten
                console.log("Vermietungen geladen:", rentals); // Debug-Log
            })
            .catch(function (error) {
                alert("Could not get your rentals");
                console.log(error);
            });
    }
</script>

<!-- Meine Vermietungen -->
<h1 class="section-title">Meine Vermietungen</h1>

<div class="row row-cols-1 row-cols-md-3 g-4">
    {#each rentals as rental}
        <div class="col">
            <div class="card h-100 shadow-sm">
                <div class="card-body">
                    <p><strong>Motorrad-Id:</strong> {rental.motorcycleId}</p>
                    <p><strong>Startdatum:</strong> {rental.startingTime}</p>
                    <p><strong>Enddatum:</strong> {rental.endingTime}</p>
                    <p><strong>Preis:</strong> {rental.price} CHF</p>
                    
                    {#if rental.status !== "AVAILABLE"}
                    <p><strong>Mieter:</strong> {rental.renterId}</p>
                        
                    {/if}
                    <p>
                        <strong>Status:</strong>
                        {#if rental.status === "AVAILABLE"}
                            <span class="badge bg-success">Verfügbar</span>
                        {:else if rental.status === "BOOKED"}
                            <span class="badge bg-warning text-dark"
                                >Gebucht</span
                            >
                        {:else if rental.status === "COMPLETED"}
                            <span class="badge bg-secondary">Abgeschlossen</span
                            >
                        {:else}
                            <span class="badge bg-dark">Unbekannt</span>
                        {/if}
                    </p>
                </div>
                <div class="card-footer bg-light text-end">
                    <button class="btn btn-outline-primary btn-sm"
                        >Details anzeigen</button
                    >
                </div>
            </div>
        </div>
    {/each}
</div>

<!-- Pagination -->
<nav class="mt-4">
    <ul class="pagination justify-content-center">
        {#each Array(nrOfPages) as _, i}
            <li class="page-item {currentPage == i + 1 ? 'active' : ''}">
                <a class="page-link" href={"/rides/myRides?page=" + (i + 1)}
                    >{i + 1}</a
                >
            </li>
        {/each}
    </ul>
</nav>

<section class="cards mt-5">
    <div class="card text-center shadow-lg p-4 rounded">
        <h2 class="card-title mb-3">Vermietung hinzufügen</h2>
        <p class="card-text">
            Füge eine neue Vermietung hinzu und profitiere von unserem
            umfassenden Service.
        </p>
        <a class="btn btn-primary btn-lg mt-2" href="/rides/ridesCreate"
            >Vermietung hinzufügen</a
        >
    </div>
</section>

<style>
    .cards {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .card {
        max-width: 400px;
        background-color: #f8f9fa; /* Hellgrauer Hintergrund */
        border: none; /* Entfernt Standardrahmen */
        border-radius: 10px; /* Abgerundete Ecken */
    }

    .card-title {
        color: #007bff; /* Blau für Titel */
        font-weight: bold;
    }

    .card-text {
        font-size: 1rem;
        color: #555; /* Dunkelgraue Schrift */
    }

    .btn-primary {
        background-color: #007bff;
        border-color: #007bff;
        transition:
            background-color 0.3s ease-in-out,
            transform 0.2s;
    }

    .btn-primary:hover {
        background-color: #0056b3;
        transform: scale(1.05); /* Leichtes Vergrößern beim Hover */
    }

    .shadow-lg {
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15); /* Schöner Schatten */
    }

    .section-title {
        font-size: 1.5rem;
        margin-top: 30px;
        border-bottom: 2px solid #007bff;
        padding-bottom: 10px;
    }
</style>
