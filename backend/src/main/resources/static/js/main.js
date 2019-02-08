/*!
 * Start Bootstrap - Resume v5.0.2 (https://startbootstrap.com/template-overviews/resume)
 * Copyright 2013-2018 Start Bootstrap
 * Licensed under MIT (https://github.com/BlackrockDigital/startbootstrap-resume/blob/master/LICENSE)
 */

!function (t) {
	"use strict";
	t('a.js-scroll-trigger[href*="#"]:not([href="#"])').click(function () {
		if (location.pathname.replace(/^\//, "") == this.pathname.replace(/^\//, "") && location.hostname == this.hostname) {
			var e = t(this.hash);
			if ((e = e.length ? e : t("[name=" + this.hash.slice(1) + "]")).length) return t("html, body").animate({scrollTop: e.offset().top}, 1e3, "easeInOutExpo"), !1
		}
	}), t(".js-scroll-trigger").click(function () {
		t(".navbar-collapse").collapse("hide")
	}), t("body").scrollspy({target: "#sideNav .js-scroll-trigger"})
}(jQuery);

let container, camera, scene, raycaster, renderer, parentTransform, sphereInter;
let mouse = new THREE.Vector2();
let radius = 100, theta = 0;

function init() {
	container = document.getElementById('container');
	camera = new THREE.PerspectiveCamera(80, window.innerWidth / window.innerHeight, 1, 1000);
	scene = new THREE.Scene();
	scene.background = new THREE.Color(0xffffff);
	let geometry = new THREE.SphereBufferGeometry(50);
	let material = new THREE.MeshBasicMaterial({color: 0xff0000});
	sphereInter = new THREE.Mesh(geometry, material);
	sphereInter.visible = false;
	scene.add(sphereInter);
	let lineGeometry = new THREE.BufferGeometry();
	let points = [];
	let point = new THREE.Vector3();
	let direction = new THREE.Vector3();
	for (let i = 0; i < 200; i++) {
		direction.x += Math.random() - 0.5;
		direction.y += Math.random() - 0.5;
		direction.z += Math.random() - 0.5;
		direction.normalize().multiplyScalar(50);
		point.add(direction);
		points.push(point.x, point.y, point.z);
	}
	lineGeometry.addAttribute('position', new THREE.Float32BufferAttribute(points, 3));
	parentTransform = new THREE.Object3D();
	parentTransform.position.x = Math.random() * 40 - 20;
	parentTransform.position.y = Math.random() * 40 - 20;
	parentTransform.position.z = Math.random() * 40 - 20;
	parentTransform.rotation.x = Math.random() * 2 * Math.PI;
	parentTransform.rotation.y = Math.random() * 2 * Math.PI;
	parentTransform.rotation.z = Math.random() * 2 * Math.PI;
	parentTransform.scale.x = Math.random() + 0.5;
	parentTransform.scale.y = Math.random() + 0.5;
	parentTransform.scale.z = Math.random() + 0.5;
	for (let i = 0; i < 200; i++) {
		let object;
		if (Math.random() > 0.5) {
			object = new THREE.Line(lineGeometry);
		} else {
			object = new THREE.LineSegments(lineGeometry);
		}
		object.position.x = Math.random() * 400 - 200;
		object.position.y = Math.random() * 400 - 200;
		object.position.z = Math.random() * 400 - 200;
		object.rotation.x = Math.random() * 2 * Math.PI;
		object.rotation.y = Math.random() * 2 * Math.PI;
		object.rotation.z = Math.random() * 2 * Math.PI;
		object.scale.x = Math.random() + 0.5;
		object.scale.y = Math.random() + 0.5;
		object.scale.z = Math.random() + 0.5;
		parentTransform.add(object);
	}
	scene.add(parentTransform);
	raycaster = new THREE.Raycaster();
	raycaster.linePrecision = 3;
	renderer = new THREE.WebGLRenderer({antialias: true});
	renderer.setPixelRatio(window.devicePixelRatio);
	renderer.setSize(window.innerWidth, window.innerHeight);
	container.appendChild(renderer.domElement);
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
	theta += 0.1;
	camera.position.x = radius * Math.sin(THREE.Math.degToRad(theta));
	camera.position.y = radius * Math.sin(THREE.Math.degToRad(theta));
	camera.position.z = radius * Math.cos(THREE.Math.degToRad(theta));
	camera.lookAt(scene.position);
	camera.updateMatrixWorld();
	raycaster.setFromCamera(mouse, camera);
	renderer.render(scene, camera);
}

init();
animate();