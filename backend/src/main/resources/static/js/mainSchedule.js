let containerSchedule, cameraSchedule, sceneSchedule, rendererSchedule;
let radiusSchedule = 500, thetaSchedule = 0;
let frustumSize = 1000;

function initSchedule() {
	containerSchedule = document.getElementById('container-schedule');
	let aspect = window.innerWidth / window.innerHeight;
	cameraSchedule = new THREE.OrthographicCamera(frustumSize * aspect / -2, frustumSize * aspect / 2, frustumSize / 2, frustumSize / -2, 1, 1000);

	sceneSchedule = new THREE.Scene();
	sceneSchedule.background = new THREE.Color(0xffffff);

	rendererSchedule = new THREE.WebGLRenderer({antialias: true});
	rendererSchedule.setSize(window.innerWidth, window.innerHeight);

	containerSchedule.appendChild(rendererSchedule.domElement);

	let light = new THREE.DirectionalLight(0xffffff, 1);
	light.position.set(1, 1, 1).normalize();
	sceneSchedule.add(light);

	let geometry = new THREE.BoxBufferGeometry(30, 30, 30);
	for (let i = 0; i < 30; i++) {
		let object = new THREE.Mesh(geometry, new THREE.MeshLambertMaterial({color: Math.random() * 0xffffff}));
		object.position.x = Math.random() * 2000 - 1000;
		object.position.y = Math.random() * 2000 - 1000;
		object.position.z = Math.random() * 1000 - 500;
		object.rotation.x = Math.random() * 2 * Math.PI;
		object.rotation.y = Math.random() * 2 * Math.PI;
		object.rotation.z = Math.random() * 2 * Math.PI;
		object.scale.x = Math.random() + 0.5;
		object.scale.y = Math.random() + 0.5;
		object.scale.z = Math.random() + 0.5;
		sceneSchedule.add(object);
	}

	window.addEventListener('resize', onWindowResize, false);
}

function onWindowResize() {
	let aspect = window.innerWidth / window.innerHeight;
	cameraSchedule.left = - frustumSize * aspect / 2;
	cameraSchedule.right = frustumSize * aspect / 2;
	cameraSchedule.top = frustumSize / 2;
	cameraSchedule.bottom = - frustumSize / 2;
	cameraSchedule.updateProjectionMatrix();
	rendererSchedule.setSize(window.innerWidth, window.innerHeight);
}

function animateSchedule() {
	requestAnimationFrame(animateSchedule);
	renderSchedule();
}

function renderSchedule() {
	thetaSchedule += 0.15;
	cameraSchedule.position.x = radiusSchedule * Math.sin( THREE.Math.degToRad( thetaSchedule ) );
	cameraSchedule.position.y = radiusSchedule * Math.sin( THREE.Math.degToRad( thetaSchedule ) );
	cameraSchedule.position.z = radiusSchedule * Math.cos( THREE.Math.degToRad( thetaSchedule ) );
	cameraSchedule.lookAt( sceneSchedule.position );
	cameraSchedule.updateMatrixWorld();

	rendererSchedule.render(sceneSchedule, cameraSchedule);
}

initSchedule();
animateSchedule();