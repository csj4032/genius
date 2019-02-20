let containerAbout, cameraAbout, sceneAbout, raycasterAbout, rendererAbout, parentTransformAbout;
let radiusAbout = 100, thetaAbout = 0;

function initAbout() {
	containerAbout = document.getElementById('container-about');
	cameraAbout = new THREE.PerspectiveCamera(80, window.innerWidth / window.innerHeight, 1, 1000);
	sceneAbout = new THREE.Scene();
	sceneAbout.background = new THREE.Color(0xffffff);
	let lineGeometry = new THREE.BufferGeometry();
	let points = [];
	let point = new THREE.Vector3();
	let direction = new THREE.Vector3();
	for (let i = 0; i < 30; i++) {
		direction.x += Math.random() - 0.5;
		direction.y += Math.random() - 0.5;
		direction.z += Math.random() - 0.5;
		direction.normalize().multiplyScalar(50);
		point.add(direction);
		points.push(point.x, point.y, point.z);
	}
	lineGeometry.addAttribute('position', new THREE.Float32BufferAttribute(points, 3));
	parentTransformAbout = new THREE.Object3D();
	parentTransformAbout.position.x = Math.random() * 40 - 20;
	parentTransformAbout.position.y = Math.random() * 40 - 20;
	parentTransformAbout.position.z = Math.random() * 40 - 20;
	parentTransformAbout.rotation.x = Math.random() * 2 * Math.PI;
	parentTransformAbout.rotation.y = Math.random() * 2 * Math.PI;
	parentTransformAbout.rotation.z = Math.random() * 2 * Math.PI;
	parentTransformAbout.scale.x = Math.random() + 1;
	parentTransformAbout.scale.y = Math.random() + 1;
	parentTransformAbout.scale.z = Math.random() + 1;
	for (let i = 0; i < 50; i++) {
		let object = new THREE.Line(lineGeometry);
		object.position.x = Math.random() * 400 - 200;
		object.position.y = Math.random() * 400 - 200;
		object.position.z = Math.random() * 400 - 200;
		object.rotation.x = Math.random() * 2 * Math.PI;
		object.rotation.y = Math.random() * 2 * Math.PI;
		object.rotation.z = Math.random() * 2 * Math.PI;
		object.scale.x = Math.random() + 1;
		object.scale.y = Math.random() + 1;
		object.scale.z = Math.random() + 1;
		parentTransformAbout.add(object);
	}
	sceneAbout.add(parentTransformAbout);
	raycasterAbout = new THREE.Raycaster();
	raycasterAbout.linePrecision = 3;
	rendererAbout = new THREE.WebGLRenderer({antialias: true});
	rendererAbout.setPixelRatio(window.devicePixelRatio);
	rendererAbout.setSize(window.innerWidth, window.innerHeight);
	containerAbout.appendChild(rendererAbout.domElement);
	window.addEventListener('resize', onWindowResize, false);
}

function onWindowResize() {
	cameraAbout.aspect = window.innerWidth / window.innerHeight;
	cameraAbout.updateProjectionMatrix();
	rendererAbout.setSize(window.innerWidth, window.innerHeight);
}

function animateAbout() {
	requestAnimationFrame(animateAbout);
	renderAbout();
}

function renderAbout() {
	thetaAbout += 0.15;
	cameraAbout.position.x = radiusAbout * Math.sin(THREE.Math.degToRad(thetaAbout));
	cameraAbout.position.y = radiusAbout * Math.sin(THREE.Math.degToRad(thetaAbout));
	cameraAbout.position.z = radiusAbout * Math.cos(THREE.Math.degToRad(thetaAbout));
	cameraAbout.lookAt(sceneAbout.position);
	cameraAbout.updateMatrixWorld();
	rendererAbout.render(sceneAbout, cameraAbout);
}

initAbout();
animateAbout();