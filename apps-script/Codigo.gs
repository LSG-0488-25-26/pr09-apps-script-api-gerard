// ==========================================
// PR09 - Spotify API amb Apps Script
// ==========================================

const SPREADSHEET_ID = SpreadsheetApp.getActiveSpreadsheet().getId();
const SONGS_SHEET = "Songs";
const REVIEWS_SHEET = "Reviews";

// ==========================================
// VALIDACIÓ DE LA API KEY
// ==========================================
function isValidApiKey(apiKey) {
  const storedKey = PropertiesService.getScriptProperties().getProperty("API_KEY");
  return apiKey === storedKey;
}

// ==========================================
// doGet(e) - ENDPOINTS GET
// ==========================================
// Endpoint 1: GET /songs          → totes les cançons
// Endpoint 2: GET /songs/search   → buscar per artista (?artist=...)
// Endpoint 3: GET /reviews        → totes les reviews
// ==========================================
function doGet(e) {
  const apiKey = e.parameter.api_key;

  if (!isValidApiKey(apiKey)) {
    return buildResponse({ error: "API Key invàlida" }, 401);
  }

  const endpoint = e.parameter.endpoint;

  if (endpoint === "songs") {
    return getSongs();

  } else if (endpoint === "songs/search") {
    const artist = e.parameter.artist;
    if (!artist) {
      return buildResponse({ error: "Falta el paràmetre 'artist'" }, 400);
    }
    return searchSongsByArtist(artist);

  } else if (endpoint === "reviews") {
    return getReviews();

  } else {
    return buildResponse({ error: "Endpoint no trobat. Usa: songs, songs/search, reviews" }, 404);
  }
}

// ==========================================
// doPost(e) - ENDPOINTS POST
// ==========================================
// Endpoint 1: POST /reviews       → afegir una review
// Endpoint 2: POST /songs         → afegir una cançó
// ==========================================
function doPost(e) {
  const body = JSON.parse(e.postData.contents);
  const apiKey = body.api_key;

  if (!isValidApiKey(apiKey)) {
    return buildResponse({ error: "API Key invàlida" }, 401);
  }

  const endpoint = body.endpoint;

  if (endpoint === "reviews") {
    return addReview(body);

  } else if (endpoint === "songs") {
    return addSong(body);

  } else {
    return buildResponse({ error: "Endpoint no trobat. Usa: reviews, songs" }, 404);
  }
}

// ==========================================
// FUNCIONS GET
// ==========================================
function getSongs() {
  const sheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName(SONGS_SHEET);
  const data = sheet.getDataRange().getValues();
  const headers = data[0];
  const rows = data.slice(1, 51); // Limitem a 50 files per no sobrecarregar

  const songs = rows.map(row => {
    let obj = {};
    headers.forEach((header, i) => {
      obj[header] = row[i];
    });
    return obj;
  });

  return buildResponse({ songs: songs, total: songs.length }, 200);
}

function searchSongsByArtist(artist) {
  const sheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName(SONGS_SHEET);
  const data = sheet.getDataRange().getValues();
  const headers = data[0];
  const rows = data.slice(1);

  // Busquem la columna d'artista
  const artistColIndex = headers.findIndex(h => 
    h.toLowerCase().includes("artist")
  );

  if (artistColIndex === -1) {
    return buildResponse({ error: "Columna d'artista no trobada" }, 500);
  }

  const filtered = rows
    .filter(row => row[artistColIndex].toString().toLowerCase().includes(artist.toLowerCase()))
    .slice(0, 20)
    .map(row => {
      let obj = {};
      headers.forEach((header, i) => { obj[header] = row[i]; });
      return obj;
    });

  return buildResponse({ songs: filtered, total: filtered.length }, 200);
}

function getReviews() {
  const sheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName(REVIEWS_SHEET);
  const data = sheet.getDataRange().getValues();
  const headers = data[0];
  const rows = data.slice(1);

  const reviews = rows.map(row => {
    let obj = {};
    headers.forEach((header, i) => { obj[header] = row[i]; });
    return obj;
  });

  return buildResponse({ reviews: reviews, total: reviews.length }, 200);
}

// ==========================================
// FUNCIONS POST
// ==========================================
function addReview(body) {
  const { track_name, artist_name, rating, comment } = body;

  if (!track_name || !artist_name || !rating) {
    return buildResponse({ error: "Falten camps: track_name, artist_name, rating" }, 400);
  }

  const sheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName(REVIEWS_SHEET);
  sheet.appendRow([track_name, artist_name, rating, comment || ""]);

  return buildResponse({ success: true, message: "Review afegida correctament" }, 200);
}

function addSong(body) {
  const { track_name, artist_name } = body;

  if (!track_name || !artist_name) {
    return buildResponse({ error: "Falten camps: track_name, artist_name" }, 400);
  }

  const sheet = SpreadsheetApp.getActiveSpreadsheet().getSheetByName(SONGS_SHEET);
  sheet.appendRow([track_name, artist_name, body.streams || 0, body.released_year || "", body.spotify_popularity || 0]);

  return buildResponse({ success: true, message: "Cançó afegida correctament" }, 200);
}

// ==========================================
// HELPER - Construir resposta JSON
// ==========================================
function buildResponse(data, statusCode) {
  return ContentService
    .createTextOutput(JSON.stringify(data))
    .setMimeType(ContentService.MimeType.JSON);
}