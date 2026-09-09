# Arquivo de comandos para compilação e execução do projeto
# LINUX - Sem Maven

echo "Compilando..."
javac -cp "core.jar" -d bin/ src/*.java src/ui/*.java src/estruturas/*.java

echo "Executando..."
java -cp "bin:core.jar:." -ea Sketch

