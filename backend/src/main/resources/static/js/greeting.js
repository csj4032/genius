function init() {
	var container = document.getElementById('container');

	camera = new THREE.PerspectiveCamera(30, window.innerWidth / window.innerHeight, 1, 5000);
	camera.position.set(0, 0, 250);

	controls = new THREE.FirstPersonControls(camera);
	controls.lookSpeed = 0.0125;
	controls.movementSpeed = 500;
	controls.noFly = false;
	controls.lookVertical = true;
	controls.constrainVertical = true;
	controls.verticalMin = 1.5;
	controls.verticalMax = 2.0;
	controls.lon = 270;
	controls.lat = 40;

	scene = new THREE.Scene();
	scene.background = new THREE.Color().setHSL(0.6, 0, 1);
	scene.fog = new THREE.Fog(scene.background, 1, 5000);
	// LIGHTS
	hemiLight = new THREE.HemisphereLight(0xffffff, 0xffffff, 0.6);
	hemiLight.color.setHSL(0.6, 1, 0.6);
	hemiLight.groundColor.setHSL(0.095, 1, 0.75);
	hemiLight.position.set(0, 50, 0);
	scene.add(hemiLight);
	hemiLightHelper = new THREE.HemisphereLightHelper(hemiLight, 10);
	scene.add(hemiLightHelper);
	dirLight = new THREE.DirectionalLight(0xffffff, 1);
	dirLight.color.setHSL(0.1, 1, 0.95);
	dirLight.position.set(-1, 1.75, 1);
	dirLight.position.multiplyScalar(30);
	scene.add(dirLight);
	dirLight.castShadow = true;
	dirLight.shadow.mapSize.width = 2048;
	dirLight.shadow.mapSize.height = 2048;

	let d = 50;
	dirLight.shadow.camera.left = -d;
	dirLight.shadow.camera.right = d;
	dirLight.shadow.camera.top = d;
	dirLight.shadow.camera.bottom = -d;
	dirLight.shadow.camera.far = 3500;
	dirLight.shadow.bias = -0.0001;
	dirLightHeper = new THREE.DirectionalLightHelper(dirLight, 10);
	scene.add(dirLightHeper);

	// GROUND
	var groundGeo = new THREE.PlaneBufferGeometry(10000, 10000);
	var groundMat = new THREE.MeshPhongMaterial({color: 0xffffff, specular: 0x050505});
	groundMat.color.setHSL(0.095, 1, 0.75);
	var ground = new THREE.Mesh(groundGeo, groundMat);
	ground.rotation.x = -Math.PI / 2;
	ground.position.y = -33;
	scene.add(ground);
	ground.receiveShadow = true;

	// SKYDOME
	var vertexShader = document.getElementById('vertexShader').textContent;
	var fragmentShader = document.getElementById('fragmentShader').textContent;
	var uniforms = {
		topColor: {value: new THREE.Color(0x0077ff)},
		bottomColor: {value: new THREE.Color(0xffffff)},
		offset: {value: 33},
		exponent: {value: 0.6}
	};
	uniforms.topColor.value.copy(hemiLight.color);
	scene.fog.color.copy(uniforms.bottomColor.value);
	var skyGeo = new THREE.SphereBufferGeometry(4000, 32, 15);
	var skyMat = new THREE.ShaderMaterial({
		vertexShader: vertexShader,
		fragmentShader: fragmentShader,
		uniforms: uniforms,
		side: THREE.BackSide
	});
	var sky = new THREE.Mesh(skyGeo, skyMat);
	scene.add(sky);

	// MODEL
	var loader = new THREE.GLTFLoader();
	loader.load('/three/models/gltf/Flamingo.glb', function (gltf) {
		var mesh = gltf.scene.children[0];
		var s = 0.35;
		mesh.scale.set(s, s, s);
		mesh.position.y = 14;
		mesh.rotation.y = -1;
		mesh.castShadow = true;
		mesh.receiveShadow = true;
		scene.add(mesh);
		var mixer = new THREE.AnimationMixer(mesh);
		mixer.clipAction(gltf.animations[0]).setDuration(1).play();
		mixers.push(mixer);
	});

	loader = new THREE.FontLoader();
	loader.load('/three/fonts/helvetiker_bold.typeface.json', function (font) {
		let textGeo = new THREE.TextBufferGeometry("Hello!", {
			font: font,
			size: 12,
			height: 10,
			curveSegments: 5,
			bevelThickness: 1,
			bevelSize: 1,
			bevelEnabled: true
		});
		textGeo.computeBoundingBox();
		let centerOffset = -0.5 * (textGeo.boundingBox.max.x - textGeo.boundingBox.min.x);
		let textMaterial = new THREE.MeshPhongMaterial({color: 0xffffff, specular: 0xffffff});
		let mesh = new THREE.Mesh(textGeo, textMaterial);
		mesh.position.x = centerOffset;
		mesh.position.y = -20;
		mesh.castShadow = true;
		mesh.receiveShadow = true;
		scene.add(mesh);
	});

	// RENDERER
	renderer = new THREE.WebGLRenderer({antialias: true});
	renderer.setPixelRatio(window.devicePixelRatio);
	renderer.setSize(window.innerWidth, window.innerHeight);
	container.appendChild(renderer.domElement);
	renderer.gammaInput = true;
	renderer.gammaOutput = true;
	renderer.shadowMap.enabled = true;

	window.addEventListener('resize', onWindowResize, false);
}

function onWindowResize() {
	camera.aspect = window.innerWidth / window.innerHeight;
	camera.updateProjectionMatrix();
	renderer.setSize(window.innerWidth, window.innerHeight);
}

function animate() {
	requestAnimationFrame(animate);
	render();
}

function render() {
	var delta = clock.getDelta();
	for (var i = 0; i < mixers.length; i++) {
		mixers[i].update(delta);
	}
	controls.update(delta);
	renderer.clear();
	renderer.render(scene, camera);
}