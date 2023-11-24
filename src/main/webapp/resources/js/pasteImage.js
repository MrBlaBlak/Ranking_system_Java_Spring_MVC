function allowDrop(event) {
    event.preventDefault();
    document.getElementById("imageDropArea").style.border = "2px dashed #aaa";
}
function drop(event) {
    event.preventDefault();
    document.getElementById("imageDropArea").style.border = "2px dashed #ccc";

    handleImage(event.dataTransfer.files[0]);
}
function paste(event) {
    let items = (event.clipboardData || event.originalEvent.clipboardData).items;

    for (let index in items) {
        let item = items[index];
        if (item.kind === 'file') {
            let blob = item.getAsFile();
            handleImage(blob);
        }
    }
}
function handleImage(file) {
    let reader = new FileReader();

    reader.onload = function (e) {
        document.getElementById("previewImage").src = e.target.result;
        document.getElementById("previewImage").style.display = "block";
    };
    reader.readAsDataURL(file);
}