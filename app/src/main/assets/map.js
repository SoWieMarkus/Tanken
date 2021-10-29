var map;


function initializeMap() {
    let mapOptions = {
        center: [51.0366, 13.7588], // Zentrum der Karte
        zoom: 13, //Zoomlevel
        layers: [] // Layer der Map, hier erstmal leer, wird gleich erweitert.
    };

    let divIdOfMap = "mapid";
    map = L.map(divIdOfMap, mapOptions);
    map.on("moveend", function () {
        try {
            let coordinates = L.latLng(map.getCenter())
            Android.onSelectLocation(coordinates.lat, coordinates.lng);
        } catch (e) {

        }
    });

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        'attribution': 'Kartendaten &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
        'useCache': true
    }).addTo(map);
}

const MARKER_LIST = [];

function clearMap() {

    while (MARKER_LIST.length !== 0) {
        map.removeLayer(MARKER_LIST.pop());
    }

}

function addMarker(coordinate, icon = undefined) {
    let marker = icon === undefined ? L.marker(coordinate) : L.marker(coordinate, {icon: icon});
    marker.addTo(map);
    MARKER_LIST.push(marker);
    return marker;
}

function showFuelStations(stations) {

    clearMap();

    for (let station of stations) {

        let coordinates = [station.lat, station.lng];
        let icon = L.divIcon({
            className: 'popup',
            html: '<span class="popuptext">' + station.e5 + '€</span>',
            iconAnchor: [23, 30]
        });

        let marker = addMarker(coordinates, icon);
        marker.on("mouseover", function () {
            focus(station);
            Android.detailStation(station.id);
        });
    }

    let group = new L.featureGroup(MARKER_LIST);
    map.fitBounds(group.getBounds());

}


function updateUserLocation(latitude, longitude) {

}

function focus(station) {
    map.flyTo([station.lat, station.lng], 15);
}

function show(node) {
    map.setView([node.x, node.y], 15);
}



