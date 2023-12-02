<?php
$baseUrl = "http://localhost:8080/getBestRoute";

$options = [
    'http' => [
        'method' => 'GET',
    ],
];

$context = stream_context_create($options);

// Appel de l'API
$response = file_get_contents($baseUrl, false, $context);

// Vérification des erreurs
if ($response === FALSE) {
    die('Erreur lors de la requête API');
}

// Affichage de la réponse
echo $response;
?>
