<?php namespace Config;

// Create a new instance of our RouteCollection class.
$routes = Services::routes();

// Load the system's routing file first, so that the app and ENVIRONMENT
// can override as needed.
if (file_exists(SYSTEMPATH . 'Config/Routes.php'))
{
	require SYSTEMPATH . 'Config/Routes.php';
}

/**
 * --------------------------------------------------------------------
 * Router Setup
 * --------------------------------------------------------------------
 */
$routes->setDefaultNamespace('App\Controllers');
$routes->setDefaultController('Home');
$routes->setDefaultMethod('index');
$routes->setTranslateURIDashes(false);
$routes->set404Override();
$routes->setAutoRoute(true);

/**
 * --------------------------------------------------------------------
 * Route Definitions
 * --------------------------------------------------------------------
 */

// We get a performance increase by specifying the default
// route since we don't have to scan directories.
$routes->get('/', 'Home::index');
$routes->options('(:any)', 'OptionsController::options');

$routes->group('api', function ($routes) {
    $routes->group('customer', function ($routes) {
        $routes->get('findall', 'CustomerRestController::findAll');
        $routes->get('find/(:any)', 'CustomerRestController::find/$1');
        $routes->post('create', 'CustomerRestController::create');
        $routes->put('update', 'CustomerRestController::update');
        $routes->delete('delete/(:any)', 'CustomerRestController::delete/$1');
    });
    $routes->group('user', function ($routes) {
        $routes->get('credential', 'UserController::exists');
    });
});
$routes->group('api', function ($routes) {
    $routes->group('user', function ($routes) {
        $routes->post('credential', 'UserController::exists');
    });
});
/**
 * --------------------------------------------------------------------
 * Additional Routing
 * --------------------------------------------------------------------
 *
 * There will often be times that you need additional routing and you
 * need it to be able to override any defaults in this file. Environment
 * based routes is one such time. require() additional route files here
 * to make that happen.
 *
 * You will have access to the $routes object within that file without
 * needing to reload it.
 */
if (file_exists(APPPATH . 'Config/' . ENVIRONMENT . '/Routes.php'))
{
	require APPPATH . 'Config/' . ENVIRONMENT . '/Routes.php';
}
