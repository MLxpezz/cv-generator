const upload_button = document.querySelector(".image-button");
const upload_input = document.getElementById("fileInput");
const image_container = document.querySelector(".image-upload-container");
const pdfButton = document.querySelector(".pdf-button");

const cvContainer = document.querySelector(".main-template-container");

//LOGICA PARA SUBIR IMAGENES A UN CONTENEDOR EN LA PAGINA DE NUEVA PLANTILLA PARA DESPUES USARLAS EN LA PLANTILLA
upload_button.addEventListener("click", () => {
    upload_input.click()
})

upload_input.addEventListener("change", (e) => {
    imageToUpload(e.target.files[0]);
})

// Prevenir el comportamiento predeterminado de arrastrar y soltar
image_container.addEventListener("dragover", (e) => {
    e.preventDefault();
    image_container.style.border = "2px dashed #000";
});

image_container.addEventListener("dragleave", (e) => {
    e.preventDefault();
    image_container.style.border = "";
});

image_container.addEventListener("drop", (e) => {
    e.preventDefault();
    image_container.style.border = "";
    imageToUpload(e.dataTransfer.files[0])
});

const imageToUpload = (e) => {
    const file = e;
    if (file && file.type.startsWith("image/")) {
        const existingImages = Array.from(image_container.querySelectorAll("img"));
        const isDuplicate = existingImages.some(img => img.getAttribute("data-file-name") === file.name);

        if (!isDuplicate) {
            const reader = new FileReader();
            reader.onload = () => {
                // Crear un nuevo elemento <img>
                const img = document.createElement("img");
                img.src = reader.result;
                img.alt = file.name; // Nombre del archivo
                img.setAttribute("data-file-name", file.name);
                img.className = "image-upload-item";
                img.style.cursor = "pointer";

                img.addEventListener("click", event => {
                    imageToCvContainer(event.target)
                })

                image_container.appendChild(img);
            };

            reader.readAsDataURL(file);
        }
    }
}

const imageToCvContainer = (event) => {
    const image = document.createElement("img");

    image.src = event.src;
    image.alt = event.alt;
    image.draggable = false;

    cvContainer.append(image);
}


//LOGICA PARA MOVER OBJETOS DENTRO DEL CONTENEDOR DEL CV
let startX = 0;
let startY = 0;
let newX = 0;
let newY = 0;
let draggableElement = null;


cvContainer.addEventListener("mouseenter", () => {
    Array.from(cvContainer.children).forEach(child => {
        child.addEventListener("mousedown", mouseDown)
    })
})

cvContainer.addEventListener("mouseleave", () => {
    cvContainer.removeEventListener("mousemove", mouseMove);
    document.removeEventListener("mouseup", mouseUp);
})

const mouseDown = (event) => {
    startX = event.clientX;
    startY = event.clientY;

    draggableElement = event.target;

    cvContainer.addEventListener("mousemove", mouseMove);
    document.addEventListener("mouseup", mouseUp);
}

const mouseMove = (event) => {
    newX = startX - event.clientX;
    newY = startY - event.clientY;

    startX = event.clientX;
    startY = event.clientY;

    draggableElement.style.top = (draggableElement.offsetTop - newY) + "px";
    draggableElement.style.left = (draggableElement.offsetLeft - newX) + "px";
}

const mouseUp = () => {
    draggableElement = null;
    cvContainer.removeEventListener("mousemove", mouseMove);
    document.removeEventListener("mouseup", mouseUp);
}

pdfButton.addEventListener("click", async (event) => {
    const response = await fetch("/pdf/generate", {
        method: "POST",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({html: cvContainer.outerHTML})
    });

    if (response.ok) {
        const blob = await response.blob();
        const link = document.createElement("a");
        link.href = window.URL.createObjectURL(blob);
        link.download = "cv.pdf";
        link.click();
        link.remove();
    } else {
        console.error("Error al generar el PDF:", response.statusText);
    }
})