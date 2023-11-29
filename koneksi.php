<?php

$servername = "localhost";
$username   = "id21585171_dimas";
$password   = "Qwe123!!";
$database   = "id21585171_coba"; //nama db

$koneksi    = mysqli_connect($servername, $username, $password, $database);

//cek jika koneksi gagal
if(mysqli_connect_errno()) {
    echo "Koneksi gagal : " . mysqli_connect_error();
}

?>