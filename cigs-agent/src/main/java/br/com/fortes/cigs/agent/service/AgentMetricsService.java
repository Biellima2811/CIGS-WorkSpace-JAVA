package br.com.fortes.cigs.agent.service;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;

/**
 * * Faz a leitura do config.ini local e conta quantos clientes estão
 * descomentados/ativos. Ajuste o caminho padrão para o local real onde o CIGS
 * salva o INI no cliente.
 */
@Service
public class AgentMetricsService {

    public int contarClientesAtivos() {
        int count = 0;
        try {
            Path path = Paths.get("C:\\CIGS\\config.ini");
            if (Files.exists(path)) {
                List<String> linhas = Files.readAllLines(path);
                for (String linha : linhas) {
                    linha = linha.trim();
                    if (!linha.startsWith(";") && !linha.startsWith("#") && linha.contains("=")) {
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível ler o config.ini - " + e.getMessage());
        }
        return count;
    }

    /**
     * Calcula o uso de RAM em porcentagem (%).
     */
    public double obterUsoMemoriaRam() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        long totalRam = osBean.getTotalMemorySize();

        long freeRam = osBean.getFreeMemorySize();

        if (totalRam == 0) {
            return 0.0;
        }

        long usedRam = totalRam - freeRam;

        return ((double) usedRam / totalRam) * 100;
    }

    /**
     * Retorna o espaço livre do disco C: em Gigabytes (GB).
     */
    public double obterEspacoLivreDiscoC() {
        File cDrive = new File("C:\\");
        long freeSpace = cDrive.getFreeSpace();
        return freeSpace / (1024.0 * 1024.0 * 1024.0);
    }
}
