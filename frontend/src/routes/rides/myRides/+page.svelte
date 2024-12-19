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

    // Variablen für die Motorräder
    let motorcycleDetails = {};

    // Variablen für die Mieter
    let renterDetails = {};

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

                // Lade die Motorrad-Details für jede Fahrt
                rentals.forEach((rental) => {
                    if (!motorcycleDetails[rental.motorcycleId]) {
                        getMotorcycleDetails(rental.motorcycleId);
                    }
                });

                // Lade die Mieter-Details nur, wenn eine renterId vorhanden ist
                rentals.forEach((rental) => {
                    if (rental.renterId && !renterDetails[rental.renterId]) {
                        getRenterDetails(rental.renterId);
                    }
                });
            })
            .catch(function (error) {
                alert("Could not get your rentals");
                console.log(error);
            });
    }

    // Funktion um die Motorrad Details zu holen
    function getMotorcycleDetails(motorcycleId) {
        var config = {
            method: "get",
            url: api_root + "/api/motorcycles/" + motorcycleId,
            headers: { Authorization: "Bearer " + $jwt_token },
        };

        axios(config)
            .then(function (response) {
                motorcycleDetails[motorcycleId] = response.data;
            })
            .catch(function (error) {
                alert("Could not get motorcycle details");
                console.log(error);
            });
    }

    // Funktion um die Renter Details zu holen
    function getRenterDetails(renterId) {
        var config = {
            method: "get",
            url: api_root + "/api/users/" + renterId,
            headers: { Authorization: "Bearer " + $jwt_token },
        };

        axios(config)
            .then(function (response) {
                renterDetails[renterId] = response.data;
            })
            .catch(function (error) {
                alert("Could not get renter details");
                console.log(error);
            });
    }

    // Funktion um eine Vermietung abzuschließen
    function completeRide(rentalId) {
        var config = {
            method: "put",
            url: api_root + "/api/me/completeRide?rideId=" + rentalId,
            headers: { Authorization: "Bearer " + $jwt_token },
        };
        axios(config)
            .then(function (response) {
                getMyRentals();
            })
            .catch(function (error) {
                alert("Konnte Vermietung nicht abschließen");
                console.log(error);
            });
    }
</script>

<!-- Meine Vermietungen -->
<h1 class="section-title">Meine Vermietungen</h1>

<div class="row row-cols-1 row-cols-md-3 g-4">
    {#each rentals as rental}
        <div class="col">
            <div class="card h-100 shadow-sm p-3">
                <div class="card-body">
                    <div class="rental-details">
                        <div class="detail-row">
                            <span class="label">Motorrad:</span>
                            <span class="value">
                                {#if motorcycleDetails[rental.motorcycleId]}
                                    {motorcycleDetails[rental.motorcycleId]
                                        .brand}
                                    {motorcycleDetails[rental.motorcycleId]
                                        .model},
                                    {motorcycleDetails[rental.motorcycleId]
                                        .year}
                                {:else}
                                    Laden...
                                {/if}
                            </span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Startdatum:</span>
                            <span class="value">{rental.startingTime}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Enddatum:</span>
                            <span class="value">{rental.endingTime}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Preis:</span>
                            <span class="value">{rental.price} CHF</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Abhol-/Rückgabeort:</span>
                            <span class="value">
                                {rental.pickupAdress.city}
                                {rental.pickupAdress.postalCode}, {rental
                                    .pickupAdress.country}<br />
                                {rental.pickupAdress.street}, {rental
                                    .pickupAdress.streetNumber}
                            </span>
                        </div>
                        {#if rental.status !== "AVAILABLE"}
                            <div class="detail-row">
                                <span class="label">Mieter:</span>
                                <span class="value">
                                    {#if renterDetails[rental.renterId]}
                                        {renterDetails[rental.renterId].firstName} {renterDetails[rental.renterId].lastName}
                                    {:else}
                                        Laden...
                                    {/if}
                                </span>
                            </div>
                            <div class="detail-row">
                                <span class="label">Email:</span>
                                <span class="value">
                                    {#if renterDetails[rental.renterId]}
                                        {renterDetails[rental.renterId].email}
                                    {:else}
                                        Laden...
                                    {/if}
                                </span>
                            </div>
                            <div class="detail-row">
                                <span class="label">Telefonnummer:</span>
                                <span class="value">
                                    {#if renterDetails[rental.renterId]}
                                        {renterDetails[rental.renterId].phoneNumber}
                                    {:else}
                                        Laden...
                                    {/if}
                                </span>
                            </div>
                        {/if}
                        <div class="detail-row">
                            <span class="label">Status:</span>
                            <span class="value">
                                {#if rental.status === "AVAILABLE"}
                                    <span class="badge bg-success"
                                        >Verfügbar</span
                                    >
                                {:else if rental.status === "BOOKED"}
                                    <span class="badge bg-warning text-dark"
                                        >Gebucht</span
                                    >
                                {:else if rental.status === "COMPLETED"}
                                    <span class="badge bg-secondary"
                                        >Abgeschlossen</span
                                    >
                                {:else}
                                    <span class="badge bg-dark">Unbekannt</span>
                                {/if}
                            </span>
                        </div>
                    </div>
                </div>
                <div class="card-footer bg-light text-end">
                    <button class="btn btn-outline-primary btn-sm"
                        >Details anzeigen</button
                    >
                    {#if rental.status === "BOOKED"}
                        <button
                            class="btn btn-outline-success btn-sm"
                            on:click={() => completeRide(rental.id)}
                            >Fahrt abschließen</button
                        >
                    {/if}
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
    .section-title {
        font-size: 1.5rem;
        margin-top: 30px;
        border-bottom: 2px solid #007bff;
        padding-bottom: 10px;
    }

    .rental-details {
        display: flex;
        flex-direction: column;
        gap: 10px; /* Abstand zwischen Zeilen */
    }

    .detail-row {
        display: flex;
        justify-content: space-between;
    }

    .label {
        font-weight: bold;
        width: 150px; /* Gleiche Breite für Beschriftungen */
        text-align: right;
        padding-right: 10px;
    }

    .value {
        text-align: left;
        flex: 1;
    }

    .card {
        max-width: 400px; /* Maximalbreite der Cards */
        background-color: #f8f9fa;
        border: none;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }

    .btn-outline-primary {
        border-color: #007bff;
        color: #007bff;
    }

    .btn-outline-primary:hover {
        background-color: #007bff;
        color: #fff;
    }

    .badge {
        font-size: 0.9rem;
        padding: 5px 10px;
        border-radius: 10px;
    }

    .shadow-lg {
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    }
</style>
