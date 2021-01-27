<?php


namespace App\Controllers;


use App\Models\CustomerModel;
use App\Models\UserModel;
use CodeIgniter\Controller;

class UserController extends Controller
{

    public function exists() {
        $mobile = $this->request->getJSON();
        $customerModel = new UserModel();
        $array = ['email' => $mobile->email, 'password' => $mobile->password];

        $account = $customerModel->where($array)->get();
        $name = null;
        foreach ($account->getResult() as $row) {$name = $row->name;}

        if(count($account->getResult()) > 0){
            return $this->response->setStatusCode(200)->setJSON(array("status" => "1" , "message" => "Bienvenido: ". $name ));
        }else{
            return $this->response->setStatusCode(200)->setJSON(array("status" => "0", "message" => "Credenciales incorrectas"));
        }



    }
}
