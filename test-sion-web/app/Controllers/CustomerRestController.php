<?php


namespace App\Controllers;


use App\Models\CustomerModel;
use CodeIgniter\Controller;

class CustomerRestController extends Controller
{
    public function findAll() {
        $customerModel = new CustomerModel();
        return $this->response->setStatusCode(200)->setJSON($customerModel->findAll());
    }

    public function find($id) {
        $customerModel = new CustomerModel();
        return $this->response->setStatusCode(200)->setJSON($customerModel->find($id));
    }

    public function create() {
        $mobile = $this->request->getJSON();
        $customerModel = new CustomerModel();
        $result = $customerModel->insert($mobile);
        if ($result) {
            return json_encode(array("message" => "Se registro correctamente"));
        }else{
            return json_encode(array("message" => "No se pudo registrar"));
        }

    }

    public function update() {
        $mobile = $this->request->getJSON();
        $customerModel = new CustomerModel();
        $result = $customerModel->update($mobile->id, $mobile);
        if ($result) {
            return json_encode(array("message" => "Se Actualizo correctamente"));
        }else{
            return json_encode(array("message" => "No se pudo actualizar"));
        }
    }
    public function delete($id) {
        $customerModel = new CustomerModel();
        $customerModel->delete($id);
        return $this->response->setStatusCode(200);
    }

}
