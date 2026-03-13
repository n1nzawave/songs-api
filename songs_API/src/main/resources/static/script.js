//создаем базовый URL для запросов
const BASE_URL = "https://gina-sleekiest-madge.ngrok-free.dev/api/song"

//Добавляем элементы HTML в JS
const songInput = document.getElementById("songInput");
const artistInput = document.getElementById("artistInput");
const fileInput = document.getElementById("fileInput");
const addBtn = document.getElementById("addBtn");
const songsList = document.getElementById("songsList");
const showSongsBtn = document.getElementById("showSongsBtn");

async function showAllSongs(){
    const response = await fetch(BASE_URL);
    const songs = await response.json();

    songsList.innerHTML = "";
    songs.forEach((song, index) => {
        const li = document.createElement("li");
        li.textContent = `Name: ${song.name}, Artist: ${song.artist}`;

        const audioPlayer = document.createElement("audio");
        audioPlayer.controls = true;

        audioPlayer.src = `${BASE_URL}/${index}/play`;

        const updateBtn = document.createElement("button");
        updateBtn.textContent = "Update";
        updateBtn.addEventListener("click", () => {
            updateSong(index);
        });

        const deleteBtn = document.createElement("button");
        deleteBtn.textContent = "Delete";
        deleteBtn.addEventListener("click", () => {
            deleteSong(index);
        });

        li.appendChild(updateBtn);
        li.appendChild(deleteBtn);
        li.appendChild(audioPlayer);

        songsList.appendChild(li)
    });
}

//Функция добавления песни
async function addSong(){
    if (!songInput.value || !fileInput.files[0]){
        alert("Enter name for song and upload file")
        return;
    }

    const formData = new FormData();
    formData.append("songName", songInput.value);
    formData.append("artist", artistInput.value);
    formData.append("file", fileInput.files[0])

    await fetch(BASE_URL, {
        method: "POST",
        body:formData
    });

    songInput.value = ""
    artistInput.value = ""
    fileInput.value = ""

    await showAllSongs()
}

async function updateSong(index){
    const newName = prompt("Enter new name for song")
    if (!newName) return;

    const updatedSong = {
        songName: newName,
        plays: 3000000,
        duration: 3.08,
        artist: "Big Baby Tape",
        label: "BENZO"
    }

    const response = await fetch(`${BASE_URL}/${index}`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(updatedSong)
    });

    if (response.status === 404){
        alert("Warning: Update failed, Yarn not found.")
        return;
    }

    await showAllSongs();
}

async function deleteSong(index){
    await fetch(`${BASE_URL}/${index}`, {
        method: "DELETE",
    });
    await showAllSongs();
}

addBtn.addEventListener("click", addSong);
showSongsBtn.addEventListener("click", showAllSongs);
