<?php


namespace App\Controllers;


use CodeIgniter\Controller;

class OptionsController extends Controller
{
    public function options() {
        return $this->response->setHeader('Access-Control-Allow-Origin', '*')
            ->setHeader('Access-Control-Allow-Methods', 'GET, POST, PATCH, PUT, DELETE, OPTIONS')
            ->setHeader('Access-Control-Allow-Headers', '*');

    }
}
