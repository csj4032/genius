let containerAbout, cameraAbout, sceneAbout, sceneAboutCss, raycasterAbout, rendererAbout, parentTransformAbout;
let radiusAbout = 100, thetaAbout = 0;

function initAbout() {
	containerAbout = document.getElementById('container-about');
	cameraAbout = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 1, 1000);
	sceneAbout = new THREE.Scene();
	sceneAbout.background = new THREE.Color(0xffffff);
	sceneAboutCss.background = new THREE.Color(0xffffff);

	let material = new THREE.MeshBasicMaterial({wireframe: true});
	let geometry = new THREE.PlaneGeometry();
	let planeMesh = new THREE.Mesh(geometry, material);
	sceneAbout.add(planeMesh);

	let element = document.getElementById('about');
	let cssObject = new THREE.CSS3DObject(element);
	cssObject.position = planeMesh.position;
	cssObject.rotation = planeMesh.rotation;
	sceneAboutCss.add(cssObject);

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